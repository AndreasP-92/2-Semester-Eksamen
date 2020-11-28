package com.example.wearegantt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

//    GET ROUTES ==================

    @GetMapping("/login")
    private String login(){

        return "login";
    }

    @GetMapping("/register")
    private String register(){
        return "register";
    }

    @GetMapping("/usertype")
    private String userType(){

        return "login/userType";
    }
//    POST ROUTES ==================

}
