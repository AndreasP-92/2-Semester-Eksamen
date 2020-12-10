package com.example.wearegantt.repository;

import com.example.wearegantt.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {

// ============================================================= PROJECTS =================================================================

    // GET ALL Projects ======================

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
                        rs.getInt(7)
                );
                allProjects.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allProjects;
    }

// GET ALL Projects WHERE ORGANIZATION ==================

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
                        rs.getInt(7)
                );
                allProjects.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allProjects;
    }

// Get ONE PROJECT WHERE PROJECT ID ===========

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
                        rs.getInt(7)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return ProjectToReturn;

    }

    // Get ONE PROJECT WITH PROJECT NAME ===========

    public Project getOneProjectWName(String project_name){
        Project ProjectToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM project WHERE project_name = ?");
            ps.setString(1 , project_name);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ProjectToReturn = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)
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


    public void InsertProject(String project_name, String project_desc, String project_duration, String project_start,String project_end, int project_fk_orgId){
//    OPRET TASK FØRST OG HENT DYNAMISK TASK ID
//    OPRET ORGANAZIATION FØRST OG HENT DYNAMISK ORG ID
//    OPRET JOBTITLE FØRST OG HENT DYNAMISK JOB ID
//    TÆNK OVER UI TIL DETTE
        try {
            System.out.println("PROJECT ===" + project_duration);
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO project(project_name, project_desc, project_duration, project_start, project_end, fk_orgId) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, project_name);
            ps.setString(2, project_desc);
            ps.setString(3, project_duration);
            ps.setString(4, project_start);
            ps.setString(5, project_end);
            ps.setInt(6, project_fk_orgId);

            int row = ps.executeUpdate();
            System.out.println("project insert");

        }catch (SQLException e){
            System.out.println(e);
        }
    }



//    ================== UPDATE Project ================

    public void updateProject(int project_id, String project_name, String project_desc, String project_duration, String project_start, String project_end, int fk_orgId){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE project SET project_name = ?, project_desc = ?, project_duration = ?, project_start = ?, project_end = ?, fk_orgId = ? WHERE project_id = ?");


            ps.setString(1, project_name);
            ps.setString(2, project_desc);
            ps.setString(3, project_duration);
            ps.setString(4, project_start);
            ps.setString(5, project_end);
            ps.setInt(6,    fk_orgId);
            ps.setInt(7,    project_id);

            int row = ps.executeUpdate();
            System.out.println("Project insert");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

//    ================== Delete Project ================

    public void deleteProject(int project_id){


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

// ============================================================= PROJECT JOB TITLES =================================================================

    //    ================== INSERT PROJECT JOB TITLE ================

//    public void insertOneProjectJobTitle(int jobTitle_id, int project_id){
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO project_jobTitle(project_id, jobTitle_id) VALUES (?, ?)");
//            ps.setInt(1, project_id);
//            ps.setInt(2, jobTitle_id);
//
//            int row = ps.executeUpdate();
//            System.out.println("Job Title insert");
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//    }
//
//    // =================== GET ONE PROJECT JOB TITLE ==================
//
//    public List<GetProjectJobTitles> getOneProjectJobTitle(int prject_id){
//        List<GetProjectJobTitles> AlljobTitles = new ArrayList<>();
//
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT project_jobTitle.projectJobTitle_id, project_jobTitle.project_id, jobTitle.jobTitle_name FROM project_jobTitle INNER JOIN jobTitle ON project_jobTitle.jobTitle_id = jobTitle.jobTitle_id WHERE project_id = ?");
//            ps.setInt(1, prject_id);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                GetProjectJobTitles tmp = new GetProjectJobTitles(
//                        rs.getInt(1),
//                        rs.getInt(2),
//                        rs.getString(3)
//                );
//                AlljobTitles.add(tmp);
//            }
//        } catch (SQLException e){
//            System.out.println(e);
//            return null;
//        }
//        return AlljobTitles;
//    }
//
//    //    ================== DELETE PROJECT JOB TITLE ================
//
//    public void deleteProjectJobTitle(int projectJobTitle_id){
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("DELETE FROM project_jobTitle WHERE projectJobTitle_id = ?");
//            ps.setInt(1, projectJobTitle_id);
//
//            int row = ps.executeUpdate();
//            System.out.println("Række slettet");
//
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//    }

// ============================================================= TASKS =================================================================

// GET ALL PROJECTS + TASKS WITH PROJECT ID ==================

    public List<Task> getAllPTasksWProjectId(int fk_projectId){
        List<Task> allTasks = new ArrayList<>();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT project_jobTitle.projectJobTitle_id, project_jobTitle.project_id,project_jobTitle.jobTitle_id, jobTitle.jobTitle_name FROM project_jobTitle INNER JOIN jobTitle ON project_jobTitle.jobTitle_id = jobTitle.jobTitle_id WHERE project_id = ?");
            ps.setInt(1, fk_projectId);

            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Task tmp = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)

                );
                allTasks.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allTasks;
    }

    public List<Task> getAllTasksWProjectId(String fk_projectName){
        List<Task> allTasks = new ArrayList<>();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM task WHERE fk_profileName = ?");
            ps.setString(1, fk_projectName);

            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Task tmp = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)

                );
                allTasks.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allTasks;
    }

//    ================== INSERT TASK ================


    public void insertTask(String task_name, String task_desc, int task_duration, String task_start,String task_end, int task_processEnd, int task_processStart, String fk_projectName, String fk_profileName, String fk_ganttPhaseName, String fk_jobTitleName){

        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO task(task_name, task_desc, task_duration, task_start, task_end, task_processEnd, task_processStart, fk_projectName,fk_profileName, fk_ganttPhaseName,fk_jobTitleName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, task_name);
            ps.setString(2, task_desc);
            ps.setInt(3, task_duration);
            ps.setString(4, task_start);
            ps.setString(5, task_end);
            ps.setInt(6, task_processEnd);
            ps.setInt(7, task_processStart);
            ps.setString(8, fk_projectName);
            ps.setString(9, fk_profileName);
            ps.setString(10, fk_ganttPhaseName);
            ps.setString(11, fk_jobTitleName);



            int row = ps.executeUpdate();
            System.out.println("project insert");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

// ============================================================= GANTT PHASES =================================================================

    // GET ALL Projects ======================

    public List<GanttPhases> getAllGanttPhases(){
        List<GanttPhases> allPhases = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM ganttPhase");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                GanttPhases tmp = new GanttPhases(
                        rs.getInt(1),
                        rs.getString(2)
                );
                allPhases.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allPhases;
    }

// ============================================================= ESTABLISH CONNECTION =================================================================

    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }
}