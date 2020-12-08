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
    @GetMapping("/admin/chat")
    public String adminChat(){
        return "admin/adminChat";
    }

    // Admin Look Up User
    @GetMapping("/admin/lookupuser")
    public String adminLookUpUser(){
        return "admin/adminLookUpUser";
    }

    @GetMapping("/admin/createuser")
    public String adminCreateUser(){
        return "admin/adminCreateUser";
    }

    //Admin organisation
    @GetMapping("/admin/organizations")
    public String adminOrganizations(){
        return "admin/adminOrganizations";
    }

    //Admin Edit Organisation
    @GetMapping("/admin/organizations/edit")
    public String adminOrganizationsEdit(){
        return "admin/adminEditOrganizations";
    }

    //Admin Projects
    @GetMapping("/admin/projects")
    public String adminProjects(){
        return "admin/adminProject";
    }

    //Admin Edit Projects
    @GetMapping("/admin/projects/edit")
    public String adminProjectsEdit(){
        return "admin/adminEditProjects";
    }

    //Admin News
    @GetMapping("/admin/news")
    public String adminNews(){
        return "admin/adminNews";
    }


//    POST ROUTES ==================

}
