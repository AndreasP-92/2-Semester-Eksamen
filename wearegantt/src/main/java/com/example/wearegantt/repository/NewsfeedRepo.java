package com.example.wearegantt.repository;

import com.example.wearegantt.model.Newsfeed;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class NewsfeedRepo {

    // =================== GET ALL NEWS ==================

    public List<Newsfeed> getAllNews(String fk_orgName){
        List<Newsfeed> allNews = new ArrayList<>();

        try {

            //lavet et statement
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM news WHERE fk_orgName = ? ");
           // metoden i ps objektet fortæller hvad der skal stå på QUERY plads nummer 1 (?).

            ps.setString(1, fk_orgName);
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
                        rs.getInt(6)
                );

                System.out.println(tmp);
                allNews.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;

        }
        return allNews;
    }

    // Get ONE NEWS ===========

    public Newsfeed getOneNews(int news_id){
        Newsfeed NewsfeedToReturn = null;


        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM news WHERE news_id = ?");
            ps.setInt(1 , news_id);


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                NewsfeedToReturn = new Newsfeed(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6)
                );
            }


        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return NewsfeedToReturn;

    }

    //    ================== UPDATE NEWS ================

    public void updateNews(int newsfeed_id, String newsfeed_news, String newsfeed_title, String newsfeed_img, String newsfeed_datetime, int fk_orgName){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE news SET newsfeed_news = ?, newsfeed_title = ?, newsfeed_img = ?, newsfeed_datetime = ?, fk_orgName = ? WHERE news_id = ?");


            ps.setString(1, newsfeed_news);
            ps.setString(2, newsfeed_title);
            ps.setString(3, newsfeed_img);
            ps.setString(4, newsfeed_datetime);
            ps.setInt(5, fk_orgName);
            ps.setInt(6,    newsfeed_id);

            int row = ps.executeUpdate();
            System.out.println("Newsfeed update");

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //    ================== Delete Project ================

    public void deleteNews(int newsfeed_id){

        NewsfeedRepo newsRepo = new NewsfeedRepo();

        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE  FROM news WHERE newsfeed_id = ?");

            ps.setInt(1,    newsfeed_id);

            int row = ps.executeUpdate();
            System.out.println("Newsfeed Deleted");

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    //    ================== INSERT NEWS ================

    public void insertNews(String newsfeed_news, String newsfeed_title, String newsfeed_img, String newsfeed_datetime,String fk_orgName){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO news(newsfeed_news, newsfeed_title, newsfeed_img, newsfeed_datetime, fk_orgName) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, newsfeed_news);
            ps.setString(2, newsfeed_title);
            ps.setString(3, newsfeed_img);
            ps.setString(4, newsfeed_datetime);
            ps.setString(5, fk_orgName);

            int row = ps.executeUpdate();
            System.out.println("news insert");

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



