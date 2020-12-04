package com.example.wearegantt.repository;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobTitleRepo {

    // =================== GET ALL JOBTITLES ==================

    public List<JobTitle> getAllJobTitle(){
        List<JobTitle> allJobTile = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                JobTitle tmp = new JobTitle(
                        rs.getInt(1),
                        rs.getString(2)

                );
                allJobTile.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allJobTile;
    }

    // =================== GET ONE JOBTITLES ==================

    public JobTitle getOneJobTitle(int jobTitle_id){
        JobTitle JobTitleToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobtitle WHERE jobtitle_id = ?");
            ps.setInt(1 , jobTitle_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                JobTitleToReturn = new JobTitle(
                        rs.getInt(1),
                        rs.getString(2)
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

    public void InsertJobTitle(String jobtitle_name){
        try {
            System.out.println("JOBTITLE ===" + jobtitle_name);
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO project(project_name) VALUES (?,)");
            ps.setString(1, jobtitle_name);


            int row = ps.executeUpdate();
            System.out.println("jobtitle insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //    ================== UPDATE JOBTITLE ================

    public void updateProject(int jobTitle_id, String jobTitle_name){
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