package edu.hitsz.enemy_factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class EliteEnemyFactory implements EnemyFactory{
    private int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1;
    private int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1;
    private int speedX = 1;
    private int speedY = 3;
    private int hp = 30;

    @Override
    public AbstractAircraft generateEnemy() {
        Random random = new Random();
        int pro = random.nextInt(2);
        if(pro == 0){
            this.speedX = speedX;
        }
        else{
            this.speedX = (-1)*speedX;
        }
        return new EliteEnemy(this.locationX, this.locationY,this.speedX, this.speedY,this.hp);

    }
}
