package com.example.wearegantt.model;

public class Authorities {
    private int auth_id;
    private String auth_role;
    private String fk_userMail;

    public Authorities(int auth_id, String auth_role, String fk_userMail) {
        this.auth_id = auth_id;
        this.auth_role = auth_role;
        this.fk_userMail = fk_userMail;
    }

    public int getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(int auth_id) {
        this.auth_id = auth_id;
    }

    public String getAuth_role() {
        return auth_role;
    }

    public void setAuth_role(String auth_role) {
        this.auth_role = auth_role;
    }

    public String getFk_userMail() {
        return fk_userMail;
    }

    public void setFk_userMail(String fk_userMail) {
        this.fk_userMail = fk_userMail;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "auth_id=" + auth_id +
                ", auth_role='" + auth_role + '\'' +
                ", fk_userMail='" + fk_userMail + '\'' +
                '}';
    }
}
