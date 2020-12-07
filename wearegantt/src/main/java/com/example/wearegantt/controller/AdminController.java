package com.example.wearegantt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class AdminController {

//    GET ROUTES ==================
    // Admin Index

    @GetMapping("/admin")
    public String admin(){
        return "admin/adminIndex";
    }

    // Admin Support
    @GetMapping("/admin/support")
    public String adminSupport(){
        return "admin/adminSupport";
    }

    //Admin Chat
    @GetMapping("/admin/support/chat")
    public String adminChat(){
        return "admin/adminChat";
    }

    // Admin Look Up User
    @GetMapping("/admin/adminLookUpUser")
    public String adminLookUpUser(){
        return "admin/adminLookUpUser";
    }

    @GetMapping("/admin/admincreateuser")
    public String adminCreateUser(){
        return "admin/adminCreateUser";
    }

    //Admin organisation
    @GetMapping("/admin/organizations")
    public String adminOrganizations(){
        return "admin/adminOrganizations";
    }

    //Admin Projects
    @GetMapping("/admin/projects")
    public String adminProjects(){
        return "admin/adminProjects";
    }

    //Admin News
    @GetMapping("/admin/newuser")
    public String adminNewsUser(){
        return "admin/adminNewsUser";
    }


//    POST ROUTES ==================

}
