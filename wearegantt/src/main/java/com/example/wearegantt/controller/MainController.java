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
        return "contact";
    }

    @GetMapping("/FAQ")
    private String faq(){
        return "faq";
    }

    @GetMapping("/privacy")
    private String privacy(){
        return "privacy";
    }

//    POST ROUTES ==================

}
