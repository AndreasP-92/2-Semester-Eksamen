package com.example.wearegantt.model;

public class Contact {
    private int ticket_id;
    private String ticket_title;
    private String ticket_context;
    private int ticket_timestamp;
    private int fk_user;

    public Contact(String ticket_title, String ticket_context, int ticket_timestamp, int fk_user) {
        this.ticket_title = ticket_title;
        this.ticket_context = ticket_context;
        this.ticket_timestamp = ticket_timestamp;
        this.fk_user = fk_user;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_title() {
        return ticket_title;
    }

    public void setTicket_title(String ticket_title) {
        this.ticket_title = ticket_title;
    }

    public String getTicket_context() {
        return ticket_context;
    }

    public void setTicket_context(String ticket_context) {
        this.ticket_context = ticket_context;
    }

    public int getTicket_timestamp() {
        return ticket_timestamp;
    }

    public void setTicket_timestamp(int ticket_timestamp) {
        this.ticket_timestamp = ticket_timestamp;
    }

    public int getFk_user() {
        return fk_user;
    }

    public void setFk_user(int fk_user) {
        this.fk_user = fk_user;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "ticket_id=" + ticket_id +
                ", ticket_title='" + ticket_title + '\'' +
                ", ticket_context='" + ticket_context + '\'' +
                ", ticket_timestamp=" + ticket_timestamp +
                ", fk_user=" + fk_user +
                '}';
    }
}
