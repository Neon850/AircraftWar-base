package edu.hitsz;

import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu {
    public JPanel MenuPanel;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JComboBox comboBox;
    private JPanel Music;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new Menu().MenuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Menu() {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (Main.panelLock){
                    Main.panelLock.notify();
                }
            }
        });
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (Main.panelLock){
                    Main.panelLock.notify();
                }
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (Main.panelLock){
                    Main.panelLock.notify();
                }
            }
        });
        comboBox.addItem("开");
        comboBox.addItem("关");
        comboBox.setSelectedIndex(1);
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    Game.musicFlag = true;
                }
            }
        });
    }
}
