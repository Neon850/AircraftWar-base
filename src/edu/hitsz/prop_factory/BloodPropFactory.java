package edu.hitsz.prop_factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory implements PropFactory {
    @Override
    public AbstractProp generateProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}
