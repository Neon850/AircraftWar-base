package edu.hitsz.bullet;

import edu.hitsz.aircraft.Subscribe;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Subscribe {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }
    @Override
    public void bomb(){
        vanish();
    }
    @Override
    public void musicEffect(){

    }

}
