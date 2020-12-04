package com.example.wearegantt.repository;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationRepo {

    // =================== GET ALL USERS ==================

    public List<Organization> getAllOrgs(){
        List<Organization> allOrgs = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM org");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Organization tmp = new Organization(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)

                );
                allOrgs.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allOrgs;
    }

// =================== GET ONE ORG ==================

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


    //    ================== INSERT ORGANIZATION ================

    public Organization insertOrg(String org_name, String org_address, int org_cvr){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO org(org_name, org_address, org_cvr) VALUES (?, ?, ?)");
            ps.setString(1, org_name);
            ps.setString(2, org_address);
            ps.setInt(3, org_cvr);

            int row = ps.executeUpdate();
            System.out.println("org insert");

        }catch (SQLException e){
            System.out.println(e);
        }

        return null;
    }

    //    ================== UPDATE ORGANIZATION ================

    public void updateOrg(int org_id, String org_name, String org_address, int org_cvr){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE org SET org_name = ?, org_address = ?, org_cvr = ? WHERE org_id = ?");
            ps.setString(1, org_name);
            ps.setString(2, org_address);
            ps.setInt(3, org_cvr);
            ps.setInt(4, org_id);

            int row = ps.executeUpdate();
            System.out.println("org insert");

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
