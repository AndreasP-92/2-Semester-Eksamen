package com.example.wearegantt.model;

public class User {
    private int user_id;
    private String user_mail;
    private String user_password;
    private int user_enabled;
    private int fk_orgId;

    public User(int user_id, String user_mail, String user_password, int user_enabled, int fk_orgId) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_password = user_password;
        this.user_enabled = user_enabled;
        this.fk_orgId = fk_orgId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_enabled() {
        return user_enabled;
    }

    public void setUser_enabled(int user_enabled) {
        this.user_enabled = user_enabled;
    }

    public int getFk_orgId() {
        return fk_orgId;
    }

    public void setFk_orgId(int fk_orgId) {
        this.fk_orgId = fk_orgId;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_mail='" + user_mail + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_enabled=" + user_enabled +
                ", fk_orgId=" + fk_orgId +
                '}';
    }
}


