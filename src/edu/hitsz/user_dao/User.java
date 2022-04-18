package edu.hitsz.user_dao;

import java.io.Serializable;

public class User implements Serializable{
    private String userName;
    private int rank;
    private int score;
    private int[] time;

    public User(int[] time,int score, String userName){
        this.time = time;
        this.score = score;
        this.userName = userName;
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

    public void setUserName(String userName){
        this.userName = userName;
    }

    public int getUserScore(){
        return score;
    }

    public void setUserScore(int score){
        this.score = score;
    }

    public String getUserTime(){
        StringBuffer sb = new StringBuffer();
        sb.append(time[0]).append('-').append(time[1]).append(" ").
                append(time[2]).append(':').append(time[3]);
        return sb.toString();
    }

}
