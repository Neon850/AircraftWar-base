package edu.hitsz.bullet;

import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;


/**
 * @Author hitsz
 */
public class HeroBullet extends BaseBullet {

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);

    }

    private MusicThread musicThread;

    @Override
    public void musicEffect(){
        if(Main.musicFlag){
            musicThread = new MusicThread("src/videos/bullet_hit.wav");
            musicThread.start();
        }
    }


    @Override
    public void bomb() {

    }
}
