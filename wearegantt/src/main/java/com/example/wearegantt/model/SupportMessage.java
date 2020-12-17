package com.example.wearegantt.model;

public class SupportMessage {
    private int message_id;
    private String message_context;
    private String message_timestamp;
    private int fk_ticketId;
    private String fk_profileFirstname;

    public SupportMessage(int message_id, String message_context, String message_timestamp, int fk_ticketId, String fk_profileFirstname) {
        this.message_id = message_id;
        this.message_context = message_context;
        this.message_timestamp = message_timestamp;
        this.fk_ticketId = fk_ticketId;
        this.fk_profileFirstname = fk_profileFirstname;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_context() {
        return message_context;
    }

    public void setMessage_context(String message_context) {
        this.message_context = message_context;
    }

    public String getMessage_timestamp() {
        return message_timestamp;
    }

    public void setMessage_timestamp(String message_timestamp) {
        this.message_timestamp = message_timestamp;
    }

    public int getFk_ticketId() {
        return fk_ticketId;
    }

    public void setFk_ticketId(int fk_ticketId) {
        this.fk_ticketId = fk_ticketId;
    }

    public String getFk_profileFirstname() {
        return fk_profileFirstname;
    }

    public void setFk_profileFirstname(String fk_profileFirstname) {
        this.fk_profileFirstname = fk_profileFirstname;
    }

    @Override
    public String toString() {
        return "SupportMessage{" +
                "message_id=" + message_id +
                ", message_context='" + message_context + '\'' +
                ", message_timestamp='" + message_timestamp + '\'' +
                ", fk_ticketId=" + fk_ticketId +
                ", fk_profileFirstname='" + fk_profileFirstname + '\'' +
                '}';
    }
}
