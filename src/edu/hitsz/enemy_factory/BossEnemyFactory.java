package edu.hitsz.enemy_factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class BossEnemyFactory implements EnemyFactory{
    private int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1;
    private int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1;
    private static int speedX = 2;
    private int baseSpeed = 1;

    private int speedY = 0;
    private static int hp = 800;
    private int baseHp = 400;


    @Override
    public AbstractAircraft generateEnemy() {
        Random random = new Random();
        int pro = random.nextInt(2);
        int currentSpeed;
        if(pro == 0){
            currentSpeed = speedX;
        }
        else{

            currentSpeed = (-1)*speedX;
        }
        return new BossEnemy(this.locationX, this.locationY,currentSpeed, this.speedY,this.hp);

    }
    @Override
    public void speedUp(){
        speedX = speedX + baseSpeed;
//        System.out.println("BossEnemy's SpeedX increase");
    }

    @Override
    public int getHp(){
        return hp;
    }
    @Override
    public void bloodUp(){
//        System.out.println("BossEnemy's Hp increase");
        this.hp = this.hp + baseHp;
    }
}
