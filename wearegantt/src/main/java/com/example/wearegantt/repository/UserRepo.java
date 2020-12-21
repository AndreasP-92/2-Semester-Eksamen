package com.example.wearegantt.repository;

import com.example.wearegantt.model.Authorities;
import com.example.wearegantt.model.AuthorityCheck;
import com.example.wearegantt.model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

// ============================================================= USER =================================================================

// *********************************************** SELECT USERS ******************************************

// =================== GET ALL USERS ==================

    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();

        try {

            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users");

            ResultSet rs = ps.executeQuery();

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

// =================== GET ALL USERS WITH ORGANIZATION ID ==================
//
//    public List<User> getAllUsersWOrgId(int fk_orgId){
//        List<User> allUsers = new ArrayList<>();
//
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users WHERE fk_orgId = ?");
//            ps.setInt(1 , fk_orgId);
//
//            ResultSet rs = ps.executeQuery();
//
//            //Bruge resultatet til noget
//            while(rs.next()){
//                User tmp = new User(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getString(3),
//                        rs.getInt(4),
//                        rs.getInt(5)
//
//                );
//                allUsers.add(tmp);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//        return allUsers;
//    }


// ========= CHECK IF USER EXISTS ===========

    public boolean checkUsernameExists(String user_mail) {
        boolean usernameExists = false;

        System.out.println("CHECKUSERNAME KØRT");

        try {

            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users WHERE user_mail = ?");
            ps.setString(1,user_mail);

            ResultSet rs =ps.executeQuery();

            String usernameCounter;

            if(rs.next()) {
                usernameCounter =  rs.getString("user_mail");

                System.out.println("USERNAMECOUNTER======"+usernameCounter);
                if(usernameCounter.equals(user_mail)) {
                    System.out.println("It already exists");
                    usernameExists = true;
                }
            }
        }

        catch (SQLException e) {
            System.out.println( e); }

        System.out.println(usernameExists);

        return usernameExists;
    }

    // ========= CHECK IF USER EXISTS 02 ===========

    public boolean checkUsernameExists02(String user_mail) {
        boolean usernameExists = false;


        try {

            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users WHERE user_mail = ?");
            ps.setString(1,user_mail);

            ResultSet rs =ps.executeQuery();

            System.out.println(rs);

            String usernameCounter;

            if(rs.next()) {
                usernameCounter =  rs.getString("user_mail");

                if(usernameCounter.equals(user_mail)) {
                    System.out.println("It does exist");

                }
            }else{
                usernameExists = true;
            }
        }

        catch (SQLException e) {
            System.out.println( e); }

        System.out.println(usernameExists);

        return usernameExists;
    }



// ========= GET ONE USER ===========


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

// ========= FIND ONE USER WITH ORGANIZATION ID ===========

    public User getOneUserWOrgId(int fk_orgId){
        User userToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users WHERE fk_orgId = ?");
            ps.setInt(1 , fk_orgId);

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

// *********************************************** INSERT USERS ******************************************

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

    //    ================== INSERT PAYMENT ================

    public void insertPayment(int payment_price, Timestamp payment_date, int fk_userId){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO payment(payment_price, payment_date, fk_userId) VALUES (?, ?, ?)");
            ps.setInt(1, payment_price);
            ps.setTimestamp(2, payment_date);
            ps.setInt(3, fk_userId);

            int row = ps.executeUpdate();
            System.out.println("payment insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

// *********************************************** UPDATE USER AUTHORITY ******************************************


    //    ================== UPDATE USER ================

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

    //    ================== UPDATE CREDENTIALS ================

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
    //    ================== UPDATE EMAIL ================

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

    //    ================== UPDATE USER ORG WITH ID ================

    public void updateUserWId(int user_id, int org_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE users SET fk_orgId = ? WHERE user_id = ?");
            ps.setInt(1, org_id);
            ps.setInt(2, user_id);

            int row = ps.executeUpdate();
            System.out.println("user updated");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    //    ================== UPDATE MAIL WITH USER ================

    public void updateMailWUserId(String user_mail, int user_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE users SET user_mail = ? WHERE user_id = ?");
            ps.setString(1, user_mail);
            ps.setInt(2, user_id);

            int row = ps.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

// *********************************************** DELETE USER ******************************************

//    ================== ADMIN DELETE ================

    public void deleteUser(int user_id){


        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE  FROM users WHERE user_id = ?");

            ps.setInt(1,    user_id);

            int row = ps.executeUpdate();
            System.out.println("User Deleted");

        }catch (SQLException e){
            System.out.println(e);
        }
    }


//    =================================================================== AUTHORITY ==========================================================================

// *********************************************** SELECT USER AUTHORITY ******************************************

//    ================== GET ALL AUTHORITY ================

    public List<AuthorityCheck> getAllAuthorities(){
        List<AuthorityCheck> allAuthorities = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM authCheck");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                AuthorityCheck tmp = new AuthorityCheck(
                        rs.getInt(1),
                        rs.getString(2)

                );
                allAuthorities.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allAuthorities;
    }

    // *********************************************** INSERT USER AUTHORITY ******************************************

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

//    ================== GET AUTH USER ROLE ================


    public Authorities getOneAuthWUserMail(String user_mail){
        Authorities authToReturn = null;

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM auth WHERE fk_userMail = ?");
            ps.setString(1 , user_mail);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                authToReturn = new Authorities(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return authToReturn;
    }

    // *********************************************** UPDATE USER AUTHORITY ******************************************

    //    ================== UPDATE AUTH ROLE ================

    public void updateAuthorities(String auth_role, String fk_userMail) {
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE auth SET auth_role = ? WHERE fk_userMail = ?");
            ps.setString(1, auth_role);
            ps.setString(2, fk_userMail);

            int row = ps.executeUpdate();
            System.out.println("UPDATE AUTH");

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

// ============================================================= ESTABLISH CONNECTION =================================================================

    private Connection establishConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }

}
