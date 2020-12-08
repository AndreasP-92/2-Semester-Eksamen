package com.example.wearegantt.repository;

import com.example.wearegantt.model.Contact;
import com.example.wearegantt.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactRepo {

// ============================================================= Ticket =================================================================

    // GET ALL Tickets ======================

    public List<Contact> getAllTickets(){
        List<Contact> allTickets = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM ticket");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Contact tmp = new Contact(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4)
                );
                allTickets.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allTickets;
    }



    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }

}
