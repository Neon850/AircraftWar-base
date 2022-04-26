package edu.hitsz.user_dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private List<User> users;

    public UserDaoImpl() {
        users = new ArrayList<>();
        try {
            File f = new File("UserData.txt");
            InputStream in = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(in);
            this.users = (List<User>)ois.readObject();
            ois.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
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
        oos.close();
    }

    @Override
    public void doDelete(int rank) throws IOException {
        int size = users.size();
        for(int i = size-1;i>=0;i--){
            User targetUser = users.get(i);
            if(targetUser.getUserRank() == rank){
                users.remove(targetUser);
            }
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UserData.txt"));
        oos.writeObject(users);
        oos.close();
    }


}
