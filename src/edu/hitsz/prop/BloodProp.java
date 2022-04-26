package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.MusicThread;

import static edu.hitsz.application.Game.musicFlag;

public class BloodProp extends AbstractProp{

    public BloodProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        if(musicFlag){
            new MusicThread("src/videos/get_supply.wav").start();
        }
        abstractAircraft.increaseHp(150);
    }



}
