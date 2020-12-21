package com.example.wearegantt.repository;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationRepo {

// =================================================================== ORGANIZATION =================================================================

// *********************************************** SELECT ORGANIZATION ******************************************

// =================== GET ALL ORGS ==================

    public List<Organization> getAllOrgs(){
        List<Organization> allOrgs = new ArrayList<>();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM org");

            ResultSet rs = ps.executeQuery();

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

// =================== GET ONE ORG WITH ORG NAME ==================

    public Organization getOneOrg(String org_name){
        Organization orgToReturn = null;

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM org WHERE org_name = ?");
            ps.setString(1 , org_name);

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

    // =================== GET ONE ORG WITH ID ==================

    public Organization getOneOrgWId(int org_id){
        Organization orgToReturn = null;

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

// *********************************************** INSERT ORGANIZATION ******************************************

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

// *********************************************** UPDATE ORGANIZATION ******************************************

//    ================== UPDATE ORGANIZATION ================

    public void updateOrg(int org_id, String org_name, String org_address, int org_cvr){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE org SET org_name = ?, org_address = ?, org_cvr = ? WHERE org_id = ?");
            ps.setString(1, org_name);
            ps.setString(2, org_address);
            ps.setInt(3, org_cvr);
            ps.setInt(4, org_id);

            int row = ps.executeUpdate();
            System.out.println("org update");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

//    ================== ADMIN UPDATE ORGANIZATION ================

    public void updateAdminOrg(int org_id, String org_name, String org_address, int org_cvr){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE org SET org_name = ?, org_address = ?, org_cvr = ? WHERE org_id = ?");
            ps.setString(1, org_name);
            ps.setString(2, org_address);
            ps.setInt(3, org_cvr);
            ps.setInt(4, org_id);

            int row = ps.executeUpdate();
            System.out.println("org Updated");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

// *********************************************** DELETE ORGANIZATION ******************************************

    //    ================== DELETE ORGANIZATION ================

    public void deleteOrg(int org_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE FROM org WHERE org_id = ?");
            ps.setInt(1, org_id);

            int row = ps.executeUpdate();
            System.out.println("ORG deleted");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

//    ================== ADMIN DELETE ORGANIZATION ================

    public void deleteAdminOrg(int org_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE FROM org WHERE org_id = ?");
            ps.setInt(1, org_id);

            int row = ps.executeUpdate();
            System.out.println("Organization Deleted");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

// ============================================================= ESTABLISH CONNECTION =================================================================


    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }
}
