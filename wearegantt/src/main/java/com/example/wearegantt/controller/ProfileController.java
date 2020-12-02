package com.example.wearegantt.controller;

import com.example.wearegantt.repository.ProfileDAO;
import com.example.wearegantt.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    //DATA ACCESS OBJECTS ====================

    @Autowired
    private ProfileDAO daoProfile;
    @Autowired
    private UserDAO daoUser;

//    GET ROUTES ==================

    @GetMapping("/editprofile")
    private String profile(){
        return "login/editprofile";
    }


    @GetMapping("/gantt")
    private String gantt(){
        return "profile/gantt";
    }


    @GetMapping("/projects")
    private String projects(){
        return "profile/project";
    }

//    POST ROUTES ==================

}
