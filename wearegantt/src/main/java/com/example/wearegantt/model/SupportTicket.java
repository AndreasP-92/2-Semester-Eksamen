package com.example.wearegantt.model;

public class SupportTicket {
    private int ticket_id;
    private String ticket_title;
    private String ticket_context;
    private String ticket_timestamp;
    private String ticket_ownerMail;
    private String ticket_ownerName;

    public SupportTicket(String ticket_title, String ticket_context, String ticket_timestamp, String ticket_ownerMail, String ticket_ownerName) {
        this.ticket_title = ticket_title;
        this.ticket_context = ticket_context;
        this.ticket_timestamp = ticket_timestamp;
        this.ticket_ownerMail = ticket_ownerMail;
        this.ticket_ownerName = ticket_ownerName;
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

    public void setTicket_desc(String ticket_desc) {
        this.ticket_context = ticket_desc;
    }

    public String getTicket_timestamp() {
        return ticket_timestamp;
    }

    public void setTicket_timestamp(String ticket_timestamp) {
        this.ticket_timestamp = ticket_timestamp;
    }

    public String getTicket_ownerMail() {
        return ticket_ownerMail;
    }

    public void setTicket_ownerMail(String ticket_ownerMail) {
        this.ticket_ownerMail = ticket_ownerMail;
    }

    public String getTicket_ownerName() {
        return ticket_ownerName;
    }

    public void setTicket_ownerName(String ticket_ownerName) {
        this.ticket_ownerName = ticket_ownerName;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticket_id=" + ticket_id +
                ", ticket_title='" + ticket_title + '\'' +
                ", ticket_context='" + ticket_context + '\'' +
                ", ticket_timestamp='" + ticket_timestamp + '\'' +
                ", ticket_ownerMail='" + ticket_ownerMail + '\'' +
                ", ticket_ownerName='" + ticket_ownerName + '\'' +
                '}';
    }
}
