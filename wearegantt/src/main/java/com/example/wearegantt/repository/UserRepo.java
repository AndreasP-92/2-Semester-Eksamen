package com.example.wearegantt.repository;

import com.example.wearegantt.model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

// =================== GET ALL USERS ==================

    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                User tmp = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)

                );
                allUsers.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allUsers;
    }


// ========= FIND ONE USER ===========

    public User getOneUser(String user_mail){
        User userToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users WHERE user_mail = ?");
            ps.setString(1 , user_mail);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                userToReturn = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return userToReturn;

    }

    // ========= GET ONE USER WITH ID ===========

    public User getOneUserWId(int user_id){
        User userToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users WHERE user_id = ?");
            ps.setInt(1 , user_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                userToReturn = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return userToReturn;

    }

//    ================== INSERT USER ================

    public void insertUser(String user_mail, String user_pass, int user_enabled){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO users(user_mail, user_password, user_enabled) VALUES (?, ?, ?)");
            ps.setString(1, user_mail);
            ps.setString(2, user_pass);
            ps.setInt(3, user_enabled);

            int row = ps.executeUpdate();
            System.out.println("user insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }


//    ================== INSERT AUTHORITY ================


    public void insertAuthUser(String user_role, String user_mail){
        String sql = "INSERT INTO auth(auth_role, fk_userMail) VALUES (?, ?)";

        try {
            PreparedStatement ps = establishConnection().prepareStatement(sql);
            ps.setString(1, user_role);
            ps.setString(2, user_mail);

            int row = ps.executeUpdate();
            System.out.println("Auth insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //    ================== Delete User ================

    public void disableUser(int user_id) {
        int user_enabled = 0;
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE users SET user_enabled = ? WHERE user_id = ?");
            ps.setInt(1, user_enabled);
            ps.setInt(2, user_id);

            int row = ps.executeUpdate();
            System.out.println("User disabled");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //    ================== UPDATE Credentials ================

    public void updateCredentials(int user_id, String user_mail, String user_password) {
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE users SET user_mail = ?, user_password = ? WHERE user_id = ?");
            ps.setString(1, user_mail);
            ps.setString(2, user_password);
            ps.setInt(3, user_id);

            int row = ps.executeUpdate();
            System.out.println("password changed");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    //    ================== UPDATE Email ================

    public void updateEmail(int user_id, String user_mail) {
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE users SET user_mail = ? WHERE user_id = ?");
            ps.setString(1, user_mail);
            ps.setInt(2, user_id);

            int row = ps.executeUpdate();
            System.out.println("email changed");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
//    ================== INSERT PROFILE ================


    public void insertProfile(String firstname, String lastname, String address, int phone, String country,int zipParsed, String jobTitle, int fk_userId){
        String sql = "INSERT INTO profile(profile_firstname,profile_lastname,profile_address,profile_phone, profile_country, profile_zip, profile_jobTitle, fk_userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = establishConnection().prepareStatement(sql);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setInt(4, phone);
            ps.setString(5, country);
            ps.setInt(6, zipParsed);
            ps.setString(7,  jobTitle);
            ps.setInt(8,  fk_userId);


            int row = ps.executeUpdate();
            System.out.println("profile inserted");

        }catch (SQLException e){
            System.out.println(e);
        }

    }
    //    ================== UPDATE USER ORG WITH ID ================

    public void updateUserWId(int user_id, int org_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE users SET fk_orgId = ? WHERE user_id = ?");
            ps.setInt(1, org_id);
            ps.setInt(2, user_id);

            int row = ps.executeUpdate();
            System.out.println( "user = "+user_id + " added to organization with ID = " + org_id);


        }catch (SQLException e){
            System.out.println(e);
        }
    }

//    ================== UPDATE USER ORG WITH MAIL ================


    public void updateUserOrg(String user_mail, int fk_orgId){
        try {
            if(fk_orgId != 0){
                PreparedStatement ps = establishConnection().prepareStatement("UPDATE user SET fk_orgId = ? WHERE user_mail = ?");
                ps.setInt(1, fk_orgId);
                ps.setString(2, user_mail);

                int row = ps.executeUpdate();
                System.out.println(user_mail + "added to organization" + fk_orgId);
            } else{

            PreparedStatement ps = establishConnection().prepareStatement("UPDATE jobTitle SET jobTitle_name = ? WHERE jobTitle_id = ?");

            int row = ps.executeUpdate();
            System.out.println("Job Title insert");
            }
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
