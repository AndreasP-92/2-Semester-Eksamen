package com.example.wearegantt.model;

public class TicketUser {
    private int ticketUser_id;
    private int ticket_id;
    private int user_id;

    public TicketUser(int ticketUser_id, int ticket_id, int user_id) {
        this.ticketUser_id = ticketUser_id;
        this.ticket_id = ticket_id;
        this.user_id = user_id;
    }

    public int getTicketUser_id() {
        return ticketUser_id;
    }

    public void setTicketUser_id(int ticketUser_id) {
        this.ticketUser_id = ticketUser_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "TicketUser{" +
                "ticketUser_id=" + ticketUser_id +
                ", ticket_id=" + ticket_id +
                ", user_id=" + user_id +
                '}';
    }
}
