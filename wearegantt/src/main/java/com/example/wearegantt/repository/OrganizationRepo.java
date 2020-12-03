package com.example.wearegantt.repository;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.User;

import java.sql.*;

public class OrganizationRepo {

// =================== GET ALL USERS ==================

    public Organization getOneOrg(int org_id){
        Organization orgToReturn = null;

        String sql = "SELECT * FROM org WHERE org_id = ?";

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM org WHERE org_id = ?");
            ps.setInt(1 , org_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                orgToReturn = new Organization(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                );
            }

        } catch (SQLException e){

            System.out.println(e);
            return null;
        }
        return orgToReturn;
    }

    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }
}
