//package com.example.wearegantt.repository;
//
//import com.example.wearegantt.model.SupportTicket;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SupportTicketRepo {
//
//
//
//// ============================================================= Ticket =================================================================
//
//    // ========= Get ONE TICKET ===========
//
//    public SupportTicket getOneTicket(String ticket_mail){
//        SupportTicket supportTicketToReturn = null;
//
//
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM supportTicket WHERE ticket_ownerMail = ?");
//            ps.setString(1 , ticket_mail);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                supportTicketToReturn = new SupportTicket(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getString(3),
//                        rs.getString(7),
//                        rs.getString(5),
//                        rs.getString(6)
//
//                );
//            }
//
//
//        }
//        catch(SQLException e){
//            System.out.println(e);
//            return null;
//        }
//
//        return supportTicketToReturn;
//
//    }
//
//
//    // GET ALL USER TICKETS ======================
//
//    public List<SupportTicket> getAllTickets(){
//        List<SupportTicket> allTickets = new ArrayList<>();
//
//        try {
//
//            //lavet et statement
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM supportTicket");
//
//            //eksekvere en query
//            ResultSet rs = ps.executeQuery();
//            System.out.println(rs);
//
//            //Bruge resultatet til noget
//            while(rs.next()){
//                SupportTicket tmp = new SupportTicket(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getString(3),
//                        rs.getString(7),
//                        rs.getString(5),
//                        rs.getString(6)
//                );
//                allTickets.add(tmp);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//        return allTickets;
//    }
//
//    //    ================== INSERT Ticket ================
//
//
//    public void InsertTicket(String ticket_title, String ticket_context, String ticket_timestamp, String ticket_ownerMail, String ticket_ownerName){
//        try {
//            System.out.println("TICKET ===" + ticket_timestamp);
//            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO supportTicket(ticket_title, ticket_context, ticket_timestamp, ticket_ownerMail, ticket_ownerName) VALUES (?, ?, ?, ?, ?)");
//            ps.setString(1, ticket_title);
//            ps.setString(2, ticket_context);
//            ps.setString(3, ticket_timestamp);
//            ps.setString(4, ticket_ownerMail);
//            ps.setString(5, ticket_ownerName);
//
//            int row = ps.executeUpdate();
//            System.out.println("ticket insert");
//
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//    }
//
//    //    ================== Delete Ticket ================
//
//    public void deleteTicket(int ticket_id){
//
//
//        SupportTicketRepo contactRepo = new SupportTicketRepo();
//
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("DELETE  FROM supportTicket WHERE ticket_id = ?");
//
//            ps.setInt(1,    ticket_id);
//
//            int row = ps.executeUpdate();
//            System.out.println("Ticket Deleted");
//
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//
//
//    }
//
//
//    private Connection establishConnection() throws SQLException {
//        //Lav en forbindelse
//        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");
//
//        return conn;
//    }
//
//}
