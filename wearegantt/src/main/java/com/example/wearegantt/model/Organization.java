package com.example.wearegantt.model;

public class Organization {
    int org_id;
    String org_name;
    String org_address;
    int org_cvr;

    public Organization(int org_id, String org_name, String org_address, int org_cvr) {
        this.org_id = org_id;
        this.org_name = org_name;
        this.org_address = org_address;
        this.org_cvr = org_cvr;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_address() {
        return org_address;
    }

    public void setOrg_address(String org_address) {
        this.org_address = org_address;
    }

    public int getOrg_cvr() {
        return org_cvr;
    }

    public void setOrg_cvr(int org_cvr) {
        this.org_cvr = org_cvr;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "org_id=" + org_id +
                ", org_name='" + org_name + '\'' +
                ", org_address='" + org_address + '\'' +
                ", org_cvr=" + org_cvr +
                '}';
    }
}
