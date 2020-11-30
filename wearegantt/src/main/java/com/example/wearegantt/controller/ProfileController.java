package com.example.wearegantt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

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
