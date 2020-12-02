package com.example.wearegantt.controller;

import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class MainController {


//    GET ROUTES ==================

    @GetMapping("/")
    private String index(){

        return "main/index";
    }

    @GetMapping("/contact")
    private String contact(){
        return "main/contact";
    }

    @GetMapping("/faq")
    private String faq(){
        return "main/faq";
    }

    @GetMapping("/privacy")
    private String privacy(){
        return "main/privacy";
    }

    @GetMapping("/newsfeed")
    private String newsfeed(){
        return "main/newsfeed";
    }
//    POST ROUTES ==================

}
