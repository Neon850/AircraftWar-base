package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.ScatterBullet;

public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        System.out.println("FireSupply active!");
        Strategy scatterBullet = new ScatterBullet();
        abstractAircraft.setStrategy(scatterBullet);
    }
}
