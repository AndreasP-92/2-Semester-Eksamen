package com.example.wearegantt.repository;

import com.example.wearegantt.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {

    // GET ALL Projects

    public List<Project> getAllProjects(){
        List<Project> allProject = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM projects");

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
                allProject.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allProject;
    }
    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }
}
