package edu.hitsz.enemy_factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory{
    private int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1;
    private int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1;
    private int speedX = 0;
    private static int speedY = 5;
    private int baseSpeed = 1;
    private static int hp = 30;
    private int baseHp = 30;

    @Override
    public AbstractAircraft generateEnemy(){
        return new MobEnemy(this.locationX, this.locationY,this.speedX, this.speedY,this.hp);
    }
    @Override
    public void speedUp(){
        speedY = speedY + baseSpeed;
//        System.out.println("MobEnemy's SpeedY increase");


    }
    @Override
    public int getHp(){
        return hp;
    }


    @Override
    public void bloodUp(){
        this.hp = this.hp + baseHp;
//        System.out.println("MobEnemy's Hp increase");
    }
}

