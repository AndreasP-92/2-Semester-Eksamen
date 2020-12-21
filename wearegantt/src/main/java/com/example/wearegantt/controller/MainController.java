package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.services.ObjectManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class MainController {

//    =================== SERVICES ==================

    ObjectManager objectManager = new ObjectManager();

//   =================================================================== GET CONTROLLER ==========================================================================

//   ================= 404 PAGE =================
//    @GetMapping("/*")
//    public String handle(Model model, Principal principal) {
//        User user                   = objectManager.userRepo.getOneUser(principal.getName());
//        System.out.println(user);
//        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
//        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());
//
//        model.addAttribute("auth", authorities);
//        model.addAttribute("org", organization);
//        model.addAttribute("user", user);
//
//        return "main/404";
//    }

//   ================= INDEX =================
    @GetMapping("/")
    private String index(){

        return "main/index";
    }

//   ================= FIRST LOGIN =================

    @GetMapping("/waiting")
    private String index(Model model, Principal principal){
        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        model.addAttribute("activePage", "projects");
        model.addAttribute("auth", authorities);
        model.addAttribute("org", organization);
        model.addAttribute("user", user);

        return "main/newUser";
    }

//   ================= CONTACT =================

    @GetMapping("/contact")
    private String contact(){
        return "main/contact";
    }

    // FAQ ==========

    @GetMapping("/faq")
    private String faq(){
        return "main/faq";
    }

    // PRIVACY ==========

    @GetMapping("/privacy")
    private String privacy(){
        return "main/privacy";
    }

//   =================================================================== POST CONTROLLER ==========================================================================

// ======================= INSERT CONTACT TICKET =======================

    @PostMapping("/insert/ticket")
    public String postTicket(WebRequest dataFromForm, Principal principal) {
        String ticket_title         = (dataFromForm.getParameter("ticket_title"));
        String ticket_ownerName     = (dataFromForm.getParameter("ticket_ownerName"));
        String ticket_ownerMail     = (dataFromForm.getParameter("ticket_ownerMail"));
        String ticket_context       = (dataFromForm.getParameter("ticket_context"));
        String user                 = dataFromForm.getRemoteUser();
        int ticket_active           = 1;
        int ticket_taken            = 0;
        int admin_replied           = 0;
        int user_replied            = 1;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        objectManager.ticketRepo.insertSupportTicket(ticket_title, ticket_context, timestamp, ticket_ownerMail, ticket_ownerName, ticket_active, ticket_taken, admin_replied, user_replied);
        if(user == null){
            return "redirect:/";
        } else{

            return "redirect:/profile/support";
        }


    }

}
