package edu.hitsz.bullet;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroBulletTest {

    private HeroBullet heroBullet;
    @BeforeEach
    void setUp() {
        heroBullet = new HeroBullet(10,10,5,5,40);

    }
    @Test
    void getPower() {
        int power = heroBullet.getPower();
        assertEquals(40,power);

    }
    @Test
    void crash() {
        BaseBullet bullet = new HeroBullet(10,10,5,5,30);
        assertTrue(heroBullet.crash(bullet));
    }

}