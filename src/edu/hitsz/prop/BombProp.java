package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;


public class BombProp extends AbstractProp {
    private List<AbstractFlyingObject> subscribeList = new ArrayList<>();
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void bomb(){

    }

    @Override
    public void addSubscribe(AbstractFlyingObject abstractFlyingObject){
        subscribeList.add(abstractFlyingObject);
    }

    @Override
    public void removeSubscribe(AbstractFlyingObject abstractFlyingObject){
        subscribeList.remove(abstractFlyingObject);
    }

    @Override
    public void notifyAllSubscribe(){
        for(AbstractFlyingObject abstractFlyingObject:subscribeList){
            abstractFlyingObject.bomb();
        }

    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        if(Main.musicFlag){
            new MusicThread("src/videos/bomb_explosion.wav").start();
        }
//        System.out.println("BombSupply active!");
        this.notifyAllSubscribe();
    }

}
