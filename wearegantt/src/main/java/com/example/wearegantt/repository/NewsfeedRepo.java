package com.example.wearegantt.repository;

import com.example.wearegantt.model.Newsfeed;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class NewsfeedRepo {

    // =================== GET ALL NEWS ==================

    public List<Newsfeed> getAllNews(){
        List<Newsfeed> allNews = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM news");

            //eksekvere en query
            ResultSet rs = ps.executeQuery();

            //Bruge resultatet til noget
            while(rs.next()){
                Newsfeed tmp = new Newsfeed(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(7)
                );
                allNews.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return allNews;
    }

    // Get ONE NEWS ===========
//
//    public Newsfeed getOneNews(int news_id){
//        Newsfeed NewsfeedToReturn = null;
//
//
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM project WHERE news_id = ?");
//            ps.setInt(1 , news_id);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                NewsfeedToReturn = new Newsfeed(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getString(3),
//                        rs.getString(4),
//                        rs.getString(5), ,
//                        rs.getInt(7)
//                );
//            }
//
//
//        }
//        catch(SQLException e){
//            System.out.println(e);
//            return null;
//        }
//
//        return NewsfeedToReturn;
//
//    }

    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");

        return conn;
    }

}



