package edu.hitsz.prop_factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory implements PropFactory {
    @Override
    public AbstractProp generateProp(int locationX, int locationY, int speedX, int speedY){
        return new BulletProp(locationX, locationY,speedX, speedY);
    }
}
