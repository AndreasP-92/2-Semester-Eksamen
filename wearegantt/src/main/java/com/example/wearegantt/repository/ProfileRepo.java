package com.example.wearegantt.repository;

import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ProfileRepo {

    // =================== GET ALL USERS ==================

    public List<Profile> getAllProfiles(){
        List<Profile> allProfiles = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM profile");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Profile tmp = new Profile(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9)
                );
                allProfiles.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allProfiles;
    }

    // =================== GET ONE PROFILE ==================

    public Profile getOneProfile(int profile_id){
        Profile ProfileToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM profile WHERE profile_id = ?");
            ps.setInt(1 , profile_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ProfileToReturn = new Profile(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return ProfileToReturn;

    }

    //    ================== INSERT PROFILE ================

    public void InsertProfile(String profile_firstname, String profile_lastname, String profile_address, int profile_phone, String profile_country, int profile_zip, String profile_jobTitle, int fk_userId){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO profile(profile_firstname, profile_lastname, profile_address, profile_phone, profile_country, profile_zip, profile_jobTitle, fk_userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, profile_firstname);
            ps.setString(2, profile_lastname);
            ps.setString(3, profile_address);
            ps.setInt(4, profile_phone);
            ps.setString(5, profile_country);
            ps.setInt(6, profile_zip);
            ps.setString(7, profile_jobTitle);
            ps.setInt(8, fk_userId);

            int row = ps.executeUpdate();
            System.out.println("profile insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }
    //    ================== UPDATE Profile ================

    public void updateProfile(int profile_id, String profile_firstname, String profile_lastname, String profile_address, int profile_phone, String profile_country, int profile_zip, String profile_jobTitle, int fk_userId){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE profile SET profile_firstname = ?, profile_lastname = ?, profile_address = ?, profile_phone = ?,profile_country = ?,profile_zip = ?, profile_jobTitle = ?, fk_userId = ? WHERE profile_id = ?");
            ps.setString(1, profile_firstname);
            ps.setString(2, profile_lastname);
            ps.setString(3, profile_address);
            ps.setInt(4, profile_phone);
            ps.setString(5, profile_country);
            ps.setInt(6, profile_zip);
            ps.setString(7, profile_jobTitle);
            ps.setInt(8, fk_userId);
            ps.setInt(9,profile_id);


            int row = ps.executeUpdate();
            System.out.println("Profile update");

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
