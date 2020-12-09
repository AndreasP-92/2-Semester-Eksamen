package com.example.wearegantt.model;

public class Newsfeed {
    private int newsfeed_id;
    private String newsfeed_news;
    private String newsfeed_title;
    private String newsfeed_img;
    private String newsfeed_datetime;
    private String fk_orgName;

    public Newsfeed(int newsfeed_id, String newsfeed_news, String newsfeed_title, String newsfeed_img, String newsfeed_datetime, String fk_orgName) {
        this.newsfeed_id = newsfeed_id;
        this.newsfeed_news = newsfeed_news;
        this.newsfeed_title = newsfeed_title;
        this.newsfeed_img = newsfeed_img;
        this.newsfeed_datetime = newsfeed_datetime;
        this.fk_orgName = fk_orgName;
    }

    public int getNewsfeed_id() {
        return newsfeed_id;
    }

    public void setNewsfeed_id(int newsfeed_id) {
        this.newsfeed_id = newsfeed_id;
    }

    public String getNewsfeed_news() {
        return newsfeed_news;
    }

    public void setNewsfeed_news(String newsfeed_news) {
        this.newsfeed_news = newsfeed_news;
    }

    public String getNewsfeed_title() {
        return newsfeed_title;
    }

    public void setNewsfeed_title(String newsfeed_title) {
        this.newsfeed_title = newsfeed_title;
    }

    public String getNewsfeed_img() {
        return newsfeed_img;
    }

    public void setNewsfeed_img(String newsfeed_img) {
        this.newsfeed_img = newsfeed_img;
    }

    public String getNewsfeed_datetime() {
        return newsfeed_datetime;
    }

    public void setNewsfeed_datetime(String newsfeed_datetime) {
        this.newsfeed_datetime = newsfeed_datetime;
    }

    public String getFk_orgName() {
        return fk_orgName;
    }

    public void setFk_orgName(String fk_orgName) {
        this.fk_orgName = fk_orgName;
    }

    @Override
    public String toString() {
        return "Newsfeed{" +
                "newsfeed_id=" + newsfeed_id +
                ", newsfeed_news='" + newsfeed_news + '\'' +
                ", newsfeed_title='" + newsfeed_title + '\'' +
                ", newsfeed_img='" + newsfeed_img + '\'' +
                ", newsfeed_datetime='" + newsfeed_datetime + '\'' +
                ", fk_orgName='" + fk_orgName + '\'' +
                '}';
    }
}
