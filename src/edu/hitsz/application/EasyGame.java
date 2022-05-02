package edu.hitsz.application;


import edu.hitsz.enemy_factory.EliteEnemyFactory;
import edu.hitsz.enemy_factory.EnemyFactory;
import edu.hitsz.enemy_factory.MobEnemyFactory;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class EasyGame extends Game {

    public EasyGame() {

    }

    @Override
    public void createEnemies(){
        EnemyFactory enemyFactory;
        double pro = Math.random();

        //随机生成精英机和普通机
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (pro < eliteEnemyCreatePro) {
                enemyFactory = new EliteEnemyFactory();
            } else {
                enemyFactory = new MobEnemyFactory();
            }
            enemyAircrafts.add(enemyFactory.generateEnemy());
        }
    }


}
