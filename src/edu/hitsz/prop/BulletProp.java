package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.MusicThread;
import edu.hitsz.strategy.DirectBullet;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.ScatterBullet;

import static edu.hitsz.application.Game.musicFlag;

public class BulletProp extends AbstractProp{
    public BulletProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    private static Thread t;
    private static boolean isScatterShoot = false;
    private int maxShootNum = 5;

    @Override
    public void influence(AbstractAircraft abstractAircraft) throws InterruptedException {
        if(musicFlag){
            new MusicThread("src/videos/bullet.wav").start();
        }
        System.out.println("FireSupply active!");

        //如果在火力道具有效期间，每触碰一次火力道具，子弹数目加1，有上限
        if(abstractAircraft.getShootNum()<maxShootNum){
            abstractAircraft.increaseShootNum();
        }

        Strategy scatterBullet = new ScatterBullet();
        Strategy directBullet = new DirectBullet();

        if(!isScatterShoot){
            t = new Thread(()->{
                try {
                    isScatterShoot = true;
                    abstractAircraft.setStrategy(scatterBullet);
                    Thread.sleep(5000);
                    abstractAircraft.setStrategy(directBullet);
                    isScatterShoot = false;

                } catch (InterruptedException e) {

                }

            });
            t.start();
        }
        else{
            t.interrupt();
            t = new Thread(()->{
                try {
                    isScatterShoot = true;
                    Thread.sleep(5000);
                    abstractAircraft.setStrategy(directBullet);
                    isScatterShoot = false;
                } catch (InterruptedException e) {

                }
            });
            t.start();
        }
    }



}
