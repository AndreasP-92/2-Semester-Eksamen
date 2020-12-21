package com.example.wearegantt.repository;

import com.example.wearegantt.model.GetProjectJobTitles;
import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobTitleRepo {


// =================================================================== JOB TITLES =================================================================

// *********************************************** SELECT JOBTITLE ******************************************

    // =================== GET ALL JOBTITLES ==================

    public List<JobTitle> getAllJobTitles(){
        List<JobTitle> allJobTitles = new ArrayList<>();

        try {


            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle");


            ResultSet rs = ps.executeQuery();


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

//    ================== GET ALL JOB TITLES WITH ORG ID ================


    public List<JobTitle> getAllJobTitlesWOrg(int fk_orgId){
        List<JobTitle> allJobTitles = new ArrayList<>();

        try {


            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle WHERE fk_orgId = ?");
            ps.setInt(1, fk_orgId);


            ResultSet rs = ps.executeQuery();


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

    // =================== GET ONE JOB TITLE WITH JOB TITLE NAME ==================

    public JobTitle getOneJobTitleWName(String jobTitle_name){
        JobTitle JobTitleToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle WHERE jobTitle_name = ?");
            ps.setString(1 , jobTitle_name);

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

//   =================== GET ONE JOB TITLE WITH JOBTITLE ID ==================

    public JobTitle getOneJobTitle(int jobTitle_id){
        JobTitle jobTitleToReturn = null;

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle WHERE jobTitle_id = ?");
            ps.setInt(1 , jobTitle_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                jobTitleToReturn = new JobTitle(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
            }
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
        return jobTitleToReturn;
    }

// =================== GET ONE JOBTITLES WITH ORGANIZATION ID ==================

    public JobTitle getOneJobTitleWOrgId(int fk_orgId){
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

// *********************************************** INSERTS JOBTITLE ******************************************


//   ================== INSERT JOBTITLE ================

    public void InsertJobTitle(String jobTitle_name, int jobTitle_fk_orgId){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO jobTitle(jobTitle_name, fk_orgId) VALUES (?,?)");
            ps.setString(1, jobTitle_name);
            ps.setInt(2, jobTitle_fk_orgId);

            int row = ps.executeUpdate();
            System.out.println("jobTitle insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

// *********************************************** UPDATE JOBTITLE ******************************************


    //    ================== UPDATE JOBTITLE ================

    public void updateJobTitle(String jobTitle_name, int jobTitle_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE jobTitle SET jobTitle_name = ? WHERE jobTitle_id = ?");
            ps.setString(1, jobTitle_name);
            ps.setInt(2, jobTitle_id);

            int row = ps.executeUpdate();
            System.out.println("JobTitle updated");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

// ============================================================= PROJECT JOB TITLES (MANY TO MANY) =================================================================

// *********************************************** SELECT PROJECT JOB TITLES ******************************************


//    ================== INSERT PROJECT JOB TITLE ================

    public void insertOneProjectJobTitle(int jobTitle_id, int project_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO project_jobTitle(project_id, jobTitle_id) VALUES (?, ?)");
            ps.setInt(1, project_id);
            ps.setInt(2, jobTitle_id);

            int row = ps.executeUpdate();
            System.out.println("Job Title insert");
        }catch (SQLException e){
            System.out.println(e);
        }
    }

// ========= CHECK IF JOB TITLES EXISTS ALREADY ===========

    public boolean checkJobTitleExists(int jobTitle) {
        boolean jobTitleExists = false;

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM project_jobTitle WHERE jobTitle_id = ?");
            ps.setInt(1,jobTitle);

            ResultSet rs =ps .executeQuery();
            int jobTitleCounter;

            if(rs.next()) {
                jobTitleCounter =  rs.getInt("jobTitle_id");

                if(jobTitleCounter == jobTitle) {
                    System.out.println("It already exists");
                    jobTitleExists = true;

                }
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("It doesn't exist already");
        return jobTitleExists;
    }

// =================== GET ONE PROJECT JOB TITLE ==================

    public List<GetProjectJobTitles> getOneProjectJobTitle(int prject_id){
        List<GetProjectJobTitles> AlljobTitles = new ArrayList<>();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT project_jobTitle.projectJobTitle_id, project_jobTitle.project_id,project_jobTitle.jobTitle_id, jobTitle.jobTitle_name FROM project_jobTitle INNER JOIN jobTitle ON project_jobTitle.jobTitle_id = jobTitle.jobTitle_id WHERE project_id = ?");
            ps.setInt(1, prject_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                GetProjectJobTitles tmp = new GetProjectJobTitles(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4)
                );
                AlljobTitles.add(tmp);
            }
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
        return AlljobTitles;
    }

// *********************************************** DELETE PROJECT JOB TITLES ******************************************


    //    ================== DELETE PROJECT JOB TITLE ================

    public void deleteProjectJobTitle(int projectJobTitle_id){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE FROM project_jobTitle WHERE projectJobTitle_id = ?");
            ps.setInt(1, projectJobTitle_id);

            int row = ps.executeUpdate();
            System.out.println("Række deleted");

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
