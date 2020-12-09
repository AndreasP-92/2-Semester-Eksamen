package com.example.wearegantt.repository;

import com.example.wearegantt.model.SupportTicket;
import com.example.wearegantt.model.TicketUser;

import java.sql.*;

public class TicketRepo {
// ============================================================= Ticket =================================================================

    //    ================== INSERT Ticket ================


    public void InsertTicketUser(int ticket_id, int user_id) {
        try {
            System.out.println("TICKET ===");
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO ticketUser(ticket_id, user_id) VALUES (?, ?)");
            ps.setInt(1, ticket_id);
            ps.setInt(2, user_id);


            int row = ps.executeUpdate();
            System.out.println("ticketUser insert");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }
}
