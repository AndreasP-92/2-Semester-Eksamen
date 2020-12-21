package com.example.wearegantt.repository;

import com.example.wearegantt.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo {
// ============================================================= TICKET =================================================================

// *********************************************** SELECT TICKETS ******************************************

    // =============== GET ALL Tickets ================

    public List<SupportTicket> getAllTickets(){
        List<SupportTicket> allTickets = new ArrayList<>();

        try {

            PreparedStatement ps = establishConnection().prepareStatement("SELECT *, DATE_FORMAT(supportTicket_timestamp, '%m/%d/%Y %H:%i') AS ticket_timestamp FROM supportTicket WHERE supportTicket_taken = 0 AND supportTicket_active = 1");

            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                SupportTicket tmp = new SupportTicket(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(11),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10)
                );
                allTickets.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allTickets;
    }

// =============== GET ALL Tickets WITH USER MAIL ================

    public List<SupportTicket> getAllTicketsWUserMail(String user_mail){
        List<SupportTicket> allTickets = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT *, DATE_FORMAT(supportTicket_timestamp, '%m/%d/%Y %H:%i') AS ticket_timestamp FROM supportTicket WHERE supportTicket_ownerMail = ? AND supportTicket_active = 1");
            ps.setString(1, user_mail);
            //eksekvere en query
            ResultSet rs = ps.executeQuery();


            //Bruge resultatet til noget
            while(rs.next()){
                SupportTicket tmp = new SupportTicket(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(11),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10)
                );
                allTickets.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allTickets;
    }


    // =============== GET ALL Tickets WITH user_id =================

    public List<GetTicketUser> getAllUserTickets(int user_id){
        List<GetTicketUser> allTickets = new ArrayList<>();

        try {

//            PreparedStatement ps = establishConnection().prepareStatement("SELECT *, DATE_FORMAT(supportTicket_timestamp, '%m/%d/%Y %H:%i') AS ticket_timestamp FROM supportTicket");

            PreparedStatement ps = establishConnection().prepareStatement("SELECT supportTicket.*,  ticket_user.*,  DATE_FORMAT(supportTicket_timestamp, '%m/%d/%Y %H:%i') AS ticket_timestamp FROM ticket_user INNER JOIN supportTicket ON ticket_user.ticket_id = supportTicket.supportTicket_id WHERE user_id = ? AND supportTicket_active = 1");
            ps.setInt(1, user_id);


            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                GetTicketUser tmp = new GetTicketUser(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(14),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12),
                        rs.getInt(13)
                );
                allTickets.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allTickets;
    }

// =============== GET ONE TICKET =================

    public SupportTicket getOneTicket(int fk_ticketId){
        SupportTicket ticketToReturn = null;

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT *, DATE_FORMAT(supportTicket_timestamp, '%m/%d/%Y %H:%i') AS ticket_timestamp FROM supportTicket WHERE supportTicket_id = ?");
            ps.setInt(1 , fk_ticketId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ticketToReturn = new SupportTicket(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(11),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10)
                );
            }
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
        return ticketToReturn;
    }

    // *********************************************** INSERT TICKETS ******************************************

//    ================== INSERT TICKET USER ================


    public void InsertTicketUser(int ticket_id, int user_id) {
        try {
            System.out.println("TICKET ===");
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO supportTicket(supportTicket_title, supportTicket_context, supportTicket_timestamp, supportTicket_ownerMail, supportTicket_ownerName) VALUES (?, ?)");
            ps.setInt(1, ticket_id);
            ps.setInt(2, user_id);


            int row = ps.executeUpdate();
            System.out.println("ticketUser insert");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    ================== ASIGN TICKET ================

    public void assignTicket(int ticket_id, int user_id) {
        try {
            System.out.println("TICKET ===");
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO ticket_user(ticket_id, user_id) VALUES (?, ?)");
            ps.setInt(1, ticket_id);
            ps.setInt(2, user_id);





            int row = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    ================== INSERT SUPPORT TICKET ================

    public void insertSupportTicket(String ticket_title, String ticket_context, Timestamp ticket_timestamp, String ticket_ownerMail, String ticket_ownerName, int ticket_active, int ticket_taken, int admin_replied, int user_replied){
        try{
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO supportTicket(supportTicket_title, supportTicket_context, supportTicket_timestamp, supportTicket_ownerMail, supportTicket_ownerName, supportTicket_active, supportTicket_taken, supportTicket_adminReplied, supportTicket_userReplied) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, ticket_title);
            ps.setString(2, ticket_context);
            ps.setTimestamp(3, ticket_timestamp);
            ps.setString(4, ticket_ownerMail);
            ps.setString(5, ticket_ownerName);
            ps.setInt(6, ticket_active);
            ps.setInt(7, ticket_taken);
            ps.setInt(8, ticket_taken);
            ps.setInt(9, ticket_taken);


            int row = ps.executeUpdate();

            System.out.println("ticket insert");

        } catch (SQLException e){
            System.out.println(e);
        }
    }

    // *********************************************** UPDATE TICKETS ******************************************

//    ================== ASIGN TICKET 02 ================

    public void assignTicket02(int ticket_id){

        try{
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE supportTicket SET supportTicket_taken = 1 WHERE supportTicket_id = ?");
            ps.setInt(1, ticket_id);

            int row = ps.executeUpdate();
            System.out.println("SUPPORT TICKET TAKEN");

        }catch (SQLException e){
            System.out.println(e);
        }

    }


//    ================== CLOSE TICKET ================

    public void closeTicket(int ticket_id){

        try{
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE supportTicket SET supportTicket_active = 0 WHERE supportTicket_id = ?");
            ps.setInt(1, ticket_id);

            int row = ps.executeUpdate();
            System.out.println("SUPPORT TICKET CLOSED");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

//    ================================================================================= MESSAGES =============================================================

// *********************************************** SELECT MESSAGES ******************************************


//    ================== INSERT SUPPORT MESSAGE ================

    public void insertMessage(String message_context, String message_timestamp, int fk_ticketId, String fk_profileFirstname){
        try{
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO supportMessage(message_context, message_timestamp, fk_ticketId, fk_profileFirstname) VALUES ( ?, ?, ?, ?)");
            ps.setString(1, message_context);
            ps.setString(2, message_timestamp);
            ps.setInt(3, fk_ticketId);
            ps.setString(4, fk_profileFirstname);

            int row = ps.executeUpdate();

            System.out.println("MESSAGE INSERT");

        } catch (SQLException e){
            System.out.println(e);
        }
    }

    //    ================== GET ONE SUPPORT MESSAGE ================

    public SupportMessage getOneMessage(int fk_ticketId){
        SupportMessage messageToReturn = null;

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM supportMessage WHERE fk_ticketId = ?");
            ps.setInt(1 , fk_ticketId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                messageToReturn = new SupportMessage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5)
                );
            }
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
        return messageToReturn;
    }

    public List<SupportMessage> getAllMessagesWTicketId(int ticket_id) {
        List<SupportMessage> allMessages = new ArrayList<>();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM supportMessage WHERE fk_ticketId = ?");
            ps.setInt(1, ticket_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SupportMessage tmp = new SupportMessage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5)
                );
                allMessages.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allMessages;
    }

// *********************************************** UPDATE MESSAGES ******************************************

    //    ================== MESSAGE UPDATE ADMIN TICKET ================

    public void messageUpdateTicketAdmin(int ticket_id) {
        try{
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE supportTicket SET supportTicket_adminReplied = 1, supportTicket_userReplied = 0 WHERE supportTicket_id = ?");
            ps.setInt(1, ticket_id);

            int row = ps.executeUpdate();
            System.out.println("ADMIN UPDATED A TICKET");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

//    ================== MESSAGE UPDATE USER TICKET ================

    public void messageUpdateTicketUser(int ticket_id) {
        try{
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE supportTicket SET supportTicket_userReplied = 1, supportTicket_adminReplied = 0 WHERE supportTicket_id = ?");
            ps.setInt(1, ticket_id);

            int row = ps.executeUpdate();
            System.out.println("USER UPDATED A TICKET");

        }catch (SQLException e){
            System.out.println(e);
        }
    }


    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }


}
