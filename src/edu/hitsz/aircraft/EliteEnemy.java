package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.enemy_factory.EnemyFactory;
import edu.hitsz.prop.*;
import edu.hitsz.prop_factory.BloodPropFactory;
import edu.hitsz.prop_factory.BombPropFactory;
import edu.hitsz.prop_factory.BulletPropFactory;
import edu.hitsz.prop_factory.PropFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class EliteEnemy extends AbstractAircraft {
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /** 攻击方式 */

    private int shootNum = 1;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)



    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*4;
        BaseBullet baseBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(baseBullet);
        }
        return res;
    }


    public void generateProp(List<AbstractProp>props){
        int X = this.getLocationX();
        int Y = this.getLocationY();

        AbstractProp prop;
        PropFactory propFactory;

        Random random = new Random();
        double pro = random.nextInt(5);
        int i = (int)Math.floor(pro);

        if(i == 0){
            propFactory = new BloodPropFactory();
            prop = propFactory.generateProp(X, Y, 0, 1) ;
            props.add(prop);
        }
        else if(i == 1){
            propFactory = new BombPropFactory();
            prop = propFactory.generateProp(X, Y, 0, 1) ;
            props.add(prop);
        }
        else if(i == 2){
            propFactory = new BulletPropFactory();
            prop = propFactory.generateProp(X, Y, 0, 1) ;
            props.add(prop);
        }
        else{
            prop = null;
        }

    }


}
