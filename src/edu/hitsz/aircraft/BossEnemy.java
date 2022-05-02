package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop_factory.BloodPropFactory;
import edu.hitsz.prop_factory.BombPropFactory;
import edu.hitsz.prop_factory.BulletPropFactory;
import edu.hitsz.prop_factory.PropFactory;
import edu.hitsz.strategy.ScatterBullet;
import edu.hitsz.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class BossEnemy extends AbstractAircraft{
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        Strategy scatterBullet = new ScatterBullet();
        this.setStrategy(scatterBullet);
        if(Main.musicFlag){
            musicThread = new MusicThread("src/videos/bgm_boss.wav");
            musicThread.start();
        }
    }

    /** 攻击方式 */

    private int shootNum = 3;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)
    private MusicThread musicThread;


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public int getShootNum() {
        return shootNum;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void vanish() {
        isValid = false;
        if(Main.musicFlag){
            musicThread.stopMusic();
        }
    }

    @Override
    public void bomb(){

    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return executeStrategy(this);
    }

    public void generateProp(List<AbstractProp>props){
        int x = this.getLocationX();
        int y = this.getLocationY();

        int propsNum = 3;
        AbstractProp prop;
        PropFactory propFactory;

        for(int i = 0; i<propsNum;i++)
        {
            Random random = new Random();
            int pro = random.nextInt(10);

            if(pro>=0 && pro<=2){
                propFactory = new BloodPropFactory();
                prop = propFactory.generateProp(x, y, 0, 2) ;
                props.add(prop);
            }
            else if(pro>=3 && pro<=5){
                propFactory = new BombPropFactory();
                prop = propFactory.generateProp(x, y, 0, 2) ;
                props.add(prop);
            }
            else if(pro>=6 && pro<=8){
                propFactory = new BulletPropFactory();
                prop = propFactory.generateProp(x, y, 0, 2) ;
                props.add(prop);
            }
            else{
                prop = null;
            }
        }
    }
}
