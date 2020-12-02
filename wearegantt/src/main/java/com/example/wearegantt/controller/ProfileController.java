package com.example.wearegantt.controller;

import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    //DATA ACCESS OBJECTS ====================

    @Autowired
    private ProfileRepo daoProfile;



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
