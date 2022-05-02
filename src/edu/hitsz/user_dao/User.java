package edu.hitsz.user_dao;

import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable{
    private String userName;
    private int rank;
    private int score;
    private int[] time;

    public User(int score, String userName){
        this.score = score;
        this.userName = userName;
        getCurrentTime();
    }

    public int getUserRank(){
        return rank;
    }

    public void setUserRank(int rank){
        this.rank = rank;
    }

    public String getUserName(){
        return userName;
    }


    public int getUserScore(){
        return score;
    }


    public String getUserTime(){


        StringBuffer sb = new StringBuffer();
        sb.append(time[0]).append('-').append(time[1]).append(" ").
                append(time[2]).append(':').append(time[3]);
        return sb.toString();
    }

    public void getCurrentTime(){
        Calendar cal=Calendar.getInstance();
        int month =cal.get(Calendar.MONTH)+1;//月(0~11)
        int date =cal.get(Calendar.DATE);//日
        int hour =cal.get(Calendar.HOUR_OF_DAY);//时
        int minute=cal.get(Calendar.MINUTE);//分
        time = new int[]{month,date,hour,minute};
    }

}
