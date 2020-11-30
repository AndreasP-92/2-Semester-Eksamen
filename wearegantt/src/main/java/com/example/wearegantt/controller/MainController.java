package com.example.wearegantt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
