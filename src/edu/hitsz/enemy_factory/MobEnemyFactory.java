package edu.hitsz.enemy_factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

public class MobEnemyFactory implements EnemyFactory{
    public AbstractAircraft generateEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        return new MobEnemy(locationX, locationY,speedX, speedY,hp);
    }
}