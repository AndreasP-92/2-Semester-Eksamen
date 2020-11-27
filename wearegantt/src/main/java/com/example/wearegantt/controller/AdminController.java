package com.example.wearegantt.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {

//    GET ROUTES ==================

    @GetMapping("/adminindex")
    private String adminIndex(){
        return "adminIndex";
    }

    @GetMapping("/admin/users")
    private String adminUsers(){
        return "adminUsers";
    }

    @GetMapping("/admin/tickets")
    private String adminTickets(){
        return "adminTickets";
    }
    
    @GetMapping("/admin/organizations")
    private String adminOrganizations(){
        return "adminOrganizations";
    }

    @GetMapping("/admin/projects")
    private String adminProjects(){
        return "adminProjects";
    }

    @GetMapping("/admin/newuser")
    private String adminNewuser(){
        return "adminNewuser";
    }


//    POST ROUTES ==================

}
