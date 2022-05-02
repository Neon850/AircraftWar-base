package edu.hitsz.application;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import edu.hitsz.BoardPanel;
import edu.hitsz.Menu;



/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static Object panelLock;
    public static boolean musicFlag = false;
    public static String gameMode;
    public static Game game;

    public static void main(String[] args) throws InterruptedException, IOException {
        panelLock = new Object();
        System.out.println("Hello Aircraft War");
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        synchronized (panelLock){
            Menu menu = new Menu();
            frame.setContentPane(menu.MenuPanel);
            frame.setVisible(true);
            panelLock.wait();
            frame.remove(menu.MenuPanel);

            frame.setContentPane(game);
            frame.setVisible(true);
            game.action();
            panelLock.wait();
            frame.remove(game);

            BoardPanel boardPanel = new BoardPanel();
            frame.setContentPane(boardPanel.BoardPanel);
            frame.setVisible(true);
            String userName = JOptionPane.showInputDialog(null,
                    "游戏结束，你的得分为："+game.score+"."+"\n请输入名字记录得分：",
                    "输入", JOptionPane.PLAIN_MESSAGE);
            boardPanel.addName(userName);
            panelLock.wait();
            frame.setContentPane(boardPanel.BoardPanel);
            frame.setVisible(true);

        }

    }
}
