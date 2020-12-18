package com.example.wearegantt.model;

public class AuthorityCheck {
    int autchcheck_id;
    String autchcheck_role;

    public AuthorityCheck(int autchcheck_id, String autchcheck_role) {
        this.autchcheck_id = autchcheck_id;
        this.autchcheck_role = autchcheck_role;
    }

    public int getAutchcheck_id() {
        return autchcheck_id;
    }

    public void setAutchcheck_id(int autchcheck_id) {
        this.autchcheck_id = autchcheck_id;
    }

    public String getAutchcheck_role() {
        return autchcheck_role;
    }

    public void setAutchcheck_role(String autchcheck_role) {
        this.autchcheck_role = autchcheck_role;
    }

    @Override
    public String toString() {
        return "AuthorityCheck{" +
                "autchcheck_id=" + autchcheck_id +
                ", autchcheck_role='" + autchcheck_role + '\'' +
                '}';
    }
}
