//package com.example.wearegantt.repository;
//
//import com.example.wearegantt.model.User;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProfileRepo2 {
//
////    This method will find all employees
//    public List<User> getAllUsers(){
//        List<User> allUsers = new ArrayList<>();
//
//        try {
//
//            //lavet et statement
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM wag_user");
//
//            //eksekvere en query
//            ResultSet rs = ps.executeQuery();
//
//            //Bruge resultatet til noget
//            while(rs.next()){
//                User tmp = new User(
//                        rs.getString(1),
//                        rs.getString(2),
//                        rs.getInt(3)
//
//                );
//                allUsers.add(tmp);
//            }
//
//        } catch (SQLException e) {
//            return null;
//        }
//        return allUsers;
//    }
//
//    public User findSingleUser(int id){
//        User userToReturn = null;
//
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM wag_user WHERE user_id = ?");
//            ps.setInt(1 , id);
//
//            ResultSet rs = ps.executeQuery();
//
//            System.out.println(rs);
//
//            while(rs.next()){
//                userToReturn = new User(
//                        rs.getString(1),
//                        rs.getString(2),
//                        rs.getInt(3)
//                );
//            }
//
//
//        }
//        catch(SQLException e){
//            System.out.println(e);
//            return null;
//        }
//        return userToReturn;
//    }
//
//    public void insertUser(String mail, String pass, int user_enabled){
//        try {
//            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO wag_user(user_mail, user_password, user_enabled) VALUES (?, ?, ?)");
//            ps.setString(1, mail);
//            ps.setString(2, pass);
//            ps.setInt(3, user_enabled);
//
//            int row = ps.executeUpdate();
//
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//
//    }
//
//
//    private Connection establishConnection() throws SQLException {
//        //Lav en forbindelse
//        Connection conn = DriverManager.getConnection("jdbc:mysql://138.197.186.159:3306/wag_app","captain","Uxr56vem.captain");
//
//        return conn;
//    }
//}
