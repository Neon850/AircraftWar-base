package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectBullet implements Strategy{
    @Override
    public List<BaseBullet> doOperation(AbstractAircraft abstractAircraft){

        List<BaseBullet> res = new LinkedList<>();
        abstractAircraft.resetShootNum(1);
        int shootNum = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        BaseBullet baseBullet;

        if(abstractAircraft instanceof HeroAircraft){

            int direction = abstractAircraft.getDirection();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + direction*2;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + direction*6;

            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                res.add(baseBullet);
            }
        }

        if(abstractAircraft instanceof EliteEnemy) {
            int direction = abstractAircraft.getDirection();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + direction*2;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + direction*4;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
                res.add(baseBullet);
            }
        }

        return res;
    }

}
