package com.example.wearegantt.repository;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobTitleRepo {

    // =================== GET ALL JOBTITLES ==================

    public List<JobTitle> getAllJobTitles(){
        List<JobTitle> allJobTitles = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                JobTitle tmp = new JobTitle(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
                allJobTitles.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allJobTitles;
    }

    // =================== GET ONE JOBTITLES ==================

    public JobTitle getOneJobTitle(int fk_orgId){
        JobTitle JobTitleToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle WHERE fk_orgId = ?");
            ps.setInt(1 , fk_orgId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                JobTitleToReturn = new JobTitle(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return JobTitleToReturn;

    }

    //    ================== INSERT JOBTITLE ================

    public void InsertJobTitle(String jobTitle_name, int jobTitle_fk_orgId){
        try {
            System.out.println("JOBTITLE ===" + jobTitle_name);
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO jobTitle(jobTitle_name, fk_orgId) VALUES (?,?)");
            ps.setString(1, jobTitle_name);
            ps.setInt(2, jobTitle_fk_orgId);

            int row = ps.executeUpdate();
            System.out.println("jobTitle insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //    ================== UPDATE JOBTITLE ================

    public void updateJobTitle(String jobTitle_name, int jobTitle_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE jobTitle SET jobTitle_name = ? WHERE jobTitle_id = ?");
            ps.setString(1, jobTitle_name);
            ps.setInt(2, jobTitle_id);

            int row = ps.executeUpdate();
            System.out.println("JobTitle insert");

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
