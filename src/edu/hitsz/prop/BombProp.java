package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.MusicThread;

import java.util.ArrayList;
import java.util.List;

import static edu.hitsz.application.Game.musicFlag;

public class BombProp extends AbstractProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }
    private List<AbstractAircraft> subscribeList = new ArrayList<>();

    public void addSubscribe(AbstractAircraft abstractAircraft){
        subscribeList.add(abstractAircraft);
    }

    public void removeSubscirbe(AbstractAircraft abstractAircraft){
        subscribeList.remove(abstractAircraft);
    }

    public void notifyAllSubscibe(){
        for(AbstractAircraft abstractAircraft:subscribeList){
            abstractAircraft.bomb();
        }
    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        if(musicFlag){
            new MusicThread("src/videos/bomb_explosion.wav").start();
        }
        System.out.println("BombSupply active!");
    }

}
