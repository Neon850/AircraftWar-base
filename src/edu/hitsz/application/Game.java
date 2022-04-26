package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.enemy_factory.BossEnemyFactory;
import edu.hitsz.enemy_factory.EliteEnemyFactory;
import edu.hitsz.enemy_factory.EnemyFactory;
import edu.hitsz.enemy_factory.MobEnemyFactory;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.user_dao.User;
import edu.hitsz.user_dao.UserDao;
import edu.hitsz.user_dao.UserDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {
    public static boolean musicFlag = false;
    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 20;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    private final int enemyMaxNumber = 5;

    public boolean gameOverFlag = false;
    public static int score = 0;
    private int bossScoreThreshold = 0;
    private boolean bossDied = true;
    private int cycleTime = 0;
    private MusicThread musicThread;

    public Game() {

        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        //添加道具
        props = new LinkedList<>();
        //Scheduled 线程池，用于定时任务调度
        ThreadFactory gameThread = r -> {
            Thread t = new Thread(r);
            t.setName("game thread");
            return t;
        };
        executorService = new ScheduledThreadPoolExecutor(1,gameThread);

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        if(musicFlag){
            musicThread = new MusicThread("src/videos/bgm.wav");
            musicThread.setRepeat();
            musicThread.start();
        }
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                //System.out.println(time);
                // 新敌机产生
                createEnemies();
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propsMoveAction();

            // 撞击检测
            try {
                crashCheckAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;

                if(musicFlag){
                    new MusicThread("src/videos/game_over.wav").start();
                    musicThread.stopMusic();
                }
                System.out.println("Game Over!");
                synchronized (Main.panelLock) {
                    Main.panelLock.notify();
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, 7, TimeUnit.MILLISECONDS);

    }



    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        /**
         * 周期（ms)
         * 指示子弹的发射、敌机的产生频率
         */
        int cycleDuration = 600;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {

        // TODO 敌机射击
        for(AbstractAircraft enemyAircraft : enemyAircrafts){
            enemyBullets.addAll(enemyAircraft.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for(AbstractProp prop : props){
            prop.forward();
        }
    }

    private boolean createBoss(int score,int bossScoreThreshold,boolean bossDied){
        //当前得分超出当前阈值一定差值且boss已死亡，避免出现多架boss机
        if(score-bossScoreThreshold >= 400 && bossDied)
        {
            this.bossDied = false;//标注boss生成
            return true;
        }
        else{
            return false;
        }
    }

    private void createEnemies(){

        double pro = Math.random();
        EnemyFactory enemyFactory;
        //随机生成精英机和普通机
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if(pro<0.6d){
                enemyFactory = new MobEnemyFactory();
            }
            else{
                enemyFactory = new EliteEnemyFactory();
            }
            enemyAircrafts.add(enemyFactory.generateEnemy());
        }

        //若boss机处于待生成状态，且分数达到，则生成boss机
        if(createBoss(score,bossScoreThreshold,bossDied)){
            enemyFactory = new BossEnemyFactory();
            //停止当前游戏bgm
            if(musicFlag){
                musicThread.stopMusic();
            }

            enemyAircrafts.add(enemyFactory.generateEnemy());
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() throws InterruptedException {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.musicEffect();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        //根据击落敌机的类型判断

                        if(enemyAircraft instanceof MobEnemy){
                            //击败普通机 score+30
                            score = score+30;

                        }
                        if(enemyAircraft instanceof BossEnemy){
                            //击败boss机 score+30
                            score = score+50;
                            bossScoreThreshold = score;//重置boss生成阈值
                            bossDied = true;//标记boss已死亡
                            if(musicFlag){
                                musicThread = new MusicThread("src/videos/bgm.wav");
                                musicThread.start();
                            }
                            enemyAircraft.vanish();
                            ((BossEnemy) enemyAircraft).generateProp(props);
                        }

                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft instanceof EliteEnemy){
                            //击败精英机 score+40;产生道具
                            score = score+40;

                            ((EliteEnemy) enemyAircraft).generateProp(props);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(AbstractProp prop : props){
            if(prop.crash(heroAircraft) || heroAircraft.crash(prop)){
                prop.vanish();
                prop.influence(heroAircraft);
            }
        }
    }



    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
