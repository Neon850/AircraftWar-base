package edu.hitsz.enemy_factory;

import edu.hitsz.aircraft.AbstractAircraft;

public interface EnemyFactory {
    AbstractAircraft generateEnemy();
    void bloodUp();
    void speedUp();
    int getHp();
}
