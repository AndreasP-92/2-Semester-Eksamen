package com.example.wearegantt.repository;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {

// ============================================================= PROJECTS =================================================================

    // GET ALL Projects

    public List<Project> getAllProjects(){
        List<Project> allProjects = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM project");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Project tmp = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)
                );
                allProjects.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allProjects;
    }

    // GET ALL Projects

    public List<Project> getAllProjectsWhere(int fk_orgId){
        List<Project> allProjects = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM project");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Project tmp = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)
                );
                allProjects.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allProjects;
    }

    // Get ONE PROJECT
    public Project getOneProject(int project_id){
        Project ProjectToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM project WHERE project_id = ?");
            ps.setInt(1 , project_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ProjectToReturn = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return ProjectToReturn;

    }

//    ================== INSERT Project ================


    public void InsertProject(String project_name, String project_desc, String project_duration, String project_start,String project_end, int project_fk_orgId, int project_fk_taskId, int project_fk_jobTitleId){
//    OPRET TASK FØRST OG HENT DYNAMISK TASK ID
//    OPRET ORGANAZIATION FØRST OG HENT DYNAMISK ORG ID
//    OPRET JOBTITLE FØRST OG HENT DYNAMISK JOB ID
//    TÆNK OVER UI TIL DETTE
        try {
            System.out.println("PROJECT ===" + project_duration);
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO project(project_name, project_desc, project_duration, project_start, project_end, fk_orgId, fk_taskId, fk_jobTitleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, project_name);
            ps.setString(2, project_desc);
            ps.setString(3, project_duration);
            ps.setString(4, project_start);
            ps.setString(5, project_end);
            ps.setInt(6, project_fk_orgId);
            ps.setInt(7, project_fk_taskId);
            ps.setInt(8, project_fk_jobTitleId);

            int row = ps.executeUpdate();
            System.out.println("project insert");

        }catch (SQLException e){
            System.out.println(e);
        }
    }



//    ================== UPDATE Project ================

    public void updateProject(int project_id, String project_name, String project_desc, String project_duration, String project_start, String project_end, int fk_orgId, int fk_taskId, int fk_jobTitleId){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE project SET project_name = ?, project_desc = ?, project_duration = ?, project_start = ?, project_end = ?, fk_orgId = ?, fk_taskId = ?, fk_jobTitleId = ? WHERE project_id = ?");


            ps.setString(1, project_name);
            ps.setString(2, project_desc);
            ps.setString(3, project_duration);
            ps.setString(4, project_start);
            ps.setString(5, project_end);
            ps.setInt(6,    fk_orgId);
            ps.setInt(7,    fk_taskId);
            ps.setInt(8,    fk_jobTitleId);
            ps.setInt(9,    project_id);

            int row = ps.executeUpdate();
            System.out.println("Project insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }
    //    ================== Delete Project ================
    public void delete(int project_id){
        String sql = "DELETE * FROM project WHERE project_id = ?";

        ProjectRepo projectRepo = new ProjectRepo();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE  FROM project WHERE project_id = ?");

            ps.setInt(1,    project_id);

            int row = ps.executeUpdate();
            System.out.println("Project Deleted");

        }catch (SQLException e){
            System.out.println(e);
        }


    }

// ============================================================= JOB TITLES =================================================================

//    ================== GET ALL JOB TITLES ================


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
                        rs.getString(2)
                );
                allJobTitles.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allJobTitles;
    }

//    ================== INSERT JOB TITLE ================

    public void insertJobTitle(String jobTitle_name){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO jobTitle(jobTitle_name) VALUES (?)");
            ps.setString(1, jobTitle_name);

            int row = ps.executeUpdate();
            System.out.println("Job Title insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    // =================== GET ONE JOB TITLE ==================

        public JobTitle getOneJobTitle(int jobTitle_id){
        JobTitle jobTitleToReturn = null;

        String sql = "SELECT * FROM org WHERE org_id = ?";

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM jobTitle WHERE jobTitle_id = ?");
            ps.setInt(1 , jobTitle_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                jobTitleToReturn = new JobTitle(
                        rs.getInt(1),
                        rs.getString(2)
                );
            }

        } catch (SQLException e){

            System.out.println(e);
            return null;
        }
        return jobTitleToReturn;
    }

    //    ================== UPDATE JOB TITLE ================

    public void updateJobTitle(int jobTitle_id, String jobTitle_name){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE jobTitle SET jobTitle_name = ? WHERE jobTitle_id = ?");
            ps.setString(1, jobTitle_name);
            ps.setInt(2, jobTitle_id);

            int row = ps.executeUpdate();
            System.out.println("Job Title insert");

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
