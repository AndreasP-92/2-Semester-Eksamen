package com.example.wearegantt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

//    GET ROUTES ==================





    @GetMapping("/profile")
    private String profile(){
        return "profile";
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
