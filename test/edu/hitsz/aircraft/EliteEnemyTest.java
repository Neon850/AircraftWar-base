package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    private volatile static HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getInstance();

    }

    @Test
    void getHp() {
        assertEquals(1000,heroAircraft.getHp());
    }

    @Test
    void decreaseHp() {
        heroAircraft.decreaseHp(3);
        assertEquals(997,heroAircraft.getHp());
    }

}