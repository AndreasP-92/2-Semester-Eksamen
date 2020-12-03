package com.example.wearegantt.repository;

import com.example.wearegantt.model.Organization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OrganizationRepo {

// =================== GET ALL USERS ==================

//    public Organization getOneOrg(int org_id){
//        Organization orgToReturn = null;
//
//        String sql = "SELECT * FROM org WHERE org_id = ?";
//
//        try {
//
//        } catch (SQLException e){
//
//            System.out.println(e);
//            return null;
//        }
//    }

    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }
}
