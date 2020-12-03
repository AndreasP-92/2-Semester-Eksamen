package com.example.wearegantt.repository;

import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {



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
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO project(project_name, project_desc, project_duration, project_start, project_end, project_fk_orgId, project_fk_taskId, fk_jobTitleId) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)");
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


    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }

}
