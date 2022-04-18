package edu.hitsz.user_dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private List<User> users;

    public UserDaoImpl() throws IOException{
        users = new ArrayList<User>();
        try {
            File f = new File("UserData.txt");
            InputStream in = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(in);
            this.users = (List<User>)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers(){
        //根据分数进行从大到小排序
        Collections.sort(users, (o1, o2) -> {
            if(o1.getUserScore()!= o2.getUserScore()){
                return o2.getUserScore()-o1.getUserScore();
            }
            else{
                return o1.getUserTime().compareTo(o2.getUserTime());
            }
        });
        //对排序后的结果，使用遍历去设置排名
        int rank=1;
        for(User user:users){
            user.setUserRank(rank);
            rank++;
        }
        return users;
    }

    @Override
    public void doAdd(User user) throws IOException {
        users.add(user);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UserData.txt"));
        oos.writeObject(users);
    }

    @Override
    public void doDelete(String userName){

    }

    @Override
    public void findByName(String userName) {

    }
}
