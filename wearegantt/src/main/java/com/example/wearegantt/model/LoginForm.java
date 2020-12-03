package com.example.wearegantt.model;

public class LoginForm {
    private String user_mail;
    private String user_password;

        public LoginForm(){
            super();
        }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public String getUser_password() {
        return user_password;
    }
}
