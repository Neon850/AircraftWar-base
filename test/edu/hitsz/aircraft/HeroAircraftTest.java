package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private volatile static HeroAircraft heroAircraft;
    private volatile static HeroAircraft heroAircraft2;

    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        heroAircraft = HeroAircraft.getInstance(
                10,10,5,4,1000);
        assertNotNull(heroAircraft);
    }

    @Test
    void getLocationX() {
        heroAircraft2 = HeroAircraft.getInstance(
                9,10,5,4,1000);
        assertEquals(9,heroAircraft2.getLocationX());
    }

}