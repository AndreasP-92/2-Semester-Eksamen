package com.example.wearegantt.repository;


import com.example.wearegantt.model.Authorities;
import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

// =================== GET ALL USERS ==================

    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM wag_user");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                User tmp = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)

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
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM wag_user WHERE user_mail = ?");
            ps.setString(1 , user_mail);

            ResultSet rs = ps.executeQuery();

            System.out.println(rs);

            while(rs.next()){
                userToReturn = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
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
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO wag_user(user_mail, user_password, user_enabled) VALUES (?, ?, ?)");
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
        String sql = "INSERT INTO wag_auth(auth_role, fk_userMail) VALUES (?, ?)";

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



//    ================== INSERT PROFILE ================


    public void insertProfile(String firstname, String lastname, String address, int phone, String country,int zipParsed, String jobTitle, int fk_userId){
        String sql = "INSERT INTO wag_profile(profile_firstname,profile_lastname,profile_adress,profile_phone, profile_country, profile_zip, profile_desc, profile_jobTitle, fk_userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = establishConnection().prepareStatement(sql);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setInt(4, phone);
            ps.setString(5, country);
            ps.setInt(6, zipParsed);
            ps.setString(7,  jobTitle);
            ps.setString(8,  "");
            ps.setInt(9,  fk_userId);


            int row = ps.executeUpdate();
            System.out.println("profile inserted");

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
