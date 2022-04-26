package edu.hitsz.bullet;

import edu.hitsz.application.MusicThread;

import static edu.hitsz.application.Game.musicFlag;

/**
 * @Author hitsz
 */
public class HeroBullet extends BaseBullet {

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);

    }

    private MusicThread musicThread;

    @Override
    public void vanish() {
        isValid = false;

    }


    @Override
    public void musicEffect(){
        if(musicFlag){
            musicThread = new MusicThread("src/videos/bullet_hit.wav");
            musicThread.start();
        }
    }


}
