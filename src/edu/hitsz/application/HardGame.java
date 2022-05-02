package edu.hitsz.application;

import edu.hitsz.enemy_factory.BossEnemyFactory;
import edu.hitsz.enemy_factory.EliteEnemyFactory;
import edu.hitsz.enemy_factory.EnemyFactory;
import edu.hitsz.enemy_factory.MobEnemyFactory;


public class HardGame extends Game {


    public HardGame() {

    }

    @Override
    public void createEnemies(){
        EnemyFactory enemyFactory;
        double pro = Math.random();

        difficultyIncrease();

        //随机生成精英机和普通机
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (pro < eliteEnemyCreatePro) {
                enemyFactory = new EliteEnemyFactory();
            } else {
                enemyFactory = new MobEnemyFactory();
            }
            enemyAircrafts.add(enemyFactory.generateEnemy());
        }
        //若boss机处于待生成状态，且分数达到，则生成boss机
        if (createBoss()) {
            bossHappened = true;
            enemyFactory = new BossEnemyFactory();
            System.out.println("产生BOSS敌机");
            System.out.println("Boss敌机血量为：" + enemyFactory.getHp());
            //停止当前游戏bgm
            if (Main.musicFlag) {
                musicThread.stopMusic();
            }

            enemyAircrafts.add(enemyFactory.generateEnemy());
            enemyFactory.bloodUp();
            enemyFactory.speedUp();
        }
    }



}
