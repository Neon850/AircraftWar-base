package edu.hitsz.enemy_factory;

import edu.hitsz.aircraft.AbstractAircraft;

public interface EnemyFactory {
    AbstractAircraft generateEnemy(int locationX, int locationY, int speedX, int speedY, int hp);
}