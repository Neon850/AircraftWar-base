package edu.hitsz;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.user_dao.User;
import edu.hitsz.user_dao.UserDao;
import edu.hitsz.user_dao.UserDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

public class BoardPanel extends JPanel{
    public JPanel BoardPanel;
    private JPanel topPanel;
    private JPanel buttonPanel;
    private JScrollPane tableScrollPanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JLabel rank;
    private JLabel gameMode;
    private String userName = null;
    private String[] columnName;
    private String[][] tableData;
    private int userNum;
    private UserDao userDao = new UserDaoImpl();

    public BoardPanel() throws IOException {
        columnName = new String[]{"名次","玩家名","得分","记录时间"};
        userNum = userDao.getAllUsers().size();
        gameMode.setText("难度："+Main.gameMode);
        createTable(userNum);
        displayPanel();
    }

    public void addName(String userName) throws IOException {

        //若用户没输入名字，也点了确认，则直接唤醒面板线程
        if(userName.equals("")){
            Main.panelLock.notify();
        }
        else{
            //新增一个用户对象
            User newUser = new User(Game.score,userName);
            userDao.doAdd(newUser);
            userNum = userDao.getAllUsers().size();
            createTable(userNum);
            displayPanel();
            Main.panelLock.notify();
        }
    }

    //将DAO写入表格
    private void createTable(int userNum){
        tableData = new String[userNum][4];
        int k=0;
        for(User user:userDao.getAllUsers()){
            tableData[k][0] = String.valueOf(user.getUserRank());
            tableData[k][1] = user.getUserName();
            tableData[k][2] = String.valueOf(user.getUserScore());
            tableData[k][3] = String.valueOf(user.getUserTime());
            k++;
        }
    }

    private void displayPanel(){
        //表格模型
        DefaultTableModel model = new DefaultTableModel(this.tableData, this.columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
//                System.out.println(row);
                if(row != -1){
                    try {
                        userDao.doDelete(row+1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    model.removeRow(row);
                }
            }
        });
    }
}
