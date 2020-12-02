package com.example.wearegantt.controller;

import com.example.wearegantt.model.Authorities;
import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.ProfileDAO;
import com.example.wearegantt.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;



@Controller
public class LoginController {

    @Autowired
    private UserDAO daoUser;
    @Autowired
    private ProfileDAO daoProfile;

//    GET ROUTES ==================

    @GetMapping("/login")
    private String login(){

        return "login/login";
    }

    @GetMapping("/register")
    private String register(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "login/register";
    }

    @PostMapping("/saveUser")
    public String postSaveUser(WebRequest dataFromForm){
        String firstname          = (dataFromForm.getParameter("firstname"));
        String lastname           = (dataFromForm.getParameter("lastname"));
        String address            = (dataFromForm.getParameter("address"));
        String phone              = (dataFromForm.getParameter("phone"));
        String country            = (dataFromForm.getParameter("country"));
        String zipcode            = (dataFromForm.getParameter("zipcode"));
        String jobTitle           = (dataFromForm.getParameter("jobTitle"));
        String password           = (dataFromForm.getParameter("password"));
        String email              = (dataFromForm.getParameter("email"));



        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        User user = new User(email,password,1);
        daoUser.save(user);
        User userObj = daoUser.getUser(email);

        Profile profile = new Profile(firstname,lastname, address, phoneParsed, country, zipParsed, "", jobTitle ,userObj.getUser_id());
        daoProfile.save(profile);

        Authorities auth = new Authorities("ROLE_USER", userObj.getUser_mail());
        daoUser.saveAuth(auth);


        return "redirect:/";
    }



    @GetMapping("/usertype")
    private String userType(){

        return "login/userType";
    }
//    POST ROUTES ==================

}
//"DELETE  FROM app_user WHERE user_mail = ?";