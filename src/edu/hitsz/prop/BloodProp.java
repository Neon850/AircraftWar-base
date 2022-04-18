package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public class BloodProp extends AbstractProp{

    public BloodProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        abstractAircraft.increaseHp(150);
    }
}
