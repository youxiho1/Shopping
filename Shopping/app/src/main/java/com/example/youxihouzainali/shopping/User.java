package com.example.youxihouzainali.shopping;

/**
 * Created by youxihouzainali on 2017/11/23.
 */

public class User {
    private int id;
    private String username;
    private String password;
    private int ownerflag;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getOwnerflag() {
        return ownerflag;
    }
    public void setOwnerflag(int ownerflag) {
        this.ownerflag = ownerflag;
    }
}
