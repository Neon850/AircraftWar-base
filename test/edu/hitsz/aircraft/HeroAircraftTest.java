package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private volatile static HeroAircraft heroAircraft;
    private volatile static HeroAircraft heroAircraft2;

    @BeforeEach
    void setUp(){
        heroAircraft2 = HeroAircraft.getInstance();
    }
    @Test
    void getInstance() {
        heroAircraft = HeroAircraft.getInstance();
        assertNotNull(heroAircraft);
    }

    @Test
    void getLocationX() {

        assertEquals(256,heroAircraft2.getLocationX());
    }

}