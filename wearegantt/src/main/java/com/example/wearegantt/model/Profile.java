package com.example.wearegantt.model;

public class Profile {
    private int profile_id;
    private String profile_firstname;
    private String profile_lastname;
    private String profile_address;
    private int profile_phone;
    private String profile_country;
    private int profile_zip;
    private String profile_jobTitle; 
    private int fk_userId;

    public Profile(int profile_id, String profile_firstname, String profile_lastname, String profile_address, int profile_phone, String profile_country, int profile_zip, String profile_jobTitle, int fk_userId) {
        this.profile_id = profile_id;
        this.profile_firstname = profile_firstname;
        this.profile_lastname = profile_lastname;
        this.profile_address = profile_address;
        this.profile_phone = profile_phone;
        this.profile_country = profile_country;
        this.profile_zip = profile_zip;
        this.profile_jobTitle = profile_jobTitle;
        this.fk_userId = fk_userId;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_firstname() {
        return profile_firstname;
    }

    public void setProfile_firstname(String profile_firstname) {
        this.profile_firstname = profile_firstname;
    }

    public String getProfile_lastname() {
        return profile_lastname;
    }

    public void setProfile_lastname(String profile_lastname) {
        this.profile_lastname = profile_lastname;
    }

    public String getProfile_address() {
        return profile_address;
    }

    public void setProfile_address(String profile_address) {
        this.profile_address = profile_address;
    }

    public int getProfile_phone() {
        return profile_phone;
    }

    public void setProfile_phone(int profile_phone) {
        this.profile_phone = profile_phone;
    }

    public String getProfile_country() {
        return profile_country;
    }

    public void setProfile_country(String profile_country) {
        this.profile_country = profile_country;
    }

    public int getProfile_zip() {
        return profile_zip;
    }

    public void setProfile_zip(int profile_zip) {
        this.profile_zip = profile_zip;
    }

    public String getProfile_jobTitle() {
        return profile_jobTitle;
    }

    public void setProfile_jobTitle(String profile_jobTitle) {
        this.profile_jobTitle = profile_jobTitle;
    }

    public int getFk_userId() {
        return fk_userId;
    }

    public void setFk_userId(int fk_userId) {
        this.fk_userId = fk_userId;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profile_id=" + profile_id +
                ", profile_firstname='" + profile_firstname + '\'' +
                ", profile_lastname='" + profile_lastname + '\'' +
                ", profile_address='" + profile_address + '\'' +
                ", profile_phone=" + profile_phone +
                ", profile_country='" + profile_country + '\'' +
                ", profile_zip=" + profile_zip +
                ", profile_jobTitle='" + profile_jobTitle + '\'' +
                ", fk_userId=" + fk_userId +
                '}';
    }
}

