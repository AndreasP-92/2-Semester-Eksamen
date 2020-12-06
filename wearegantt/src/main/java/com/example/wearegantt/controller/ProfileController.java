package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ProfileController {

//REPOSITORIES ====================

    OrganizationRepo orgRep = new OrganizationRepo();

    UserRepo userRepo = new UserRepo();

    ProfileRepo profileRepo = new ProfileRepo();


//    GET ROUTES ==================

// EDIT PROFILE =================


    @GetMapping("/editprofile/{user_mail}")
    private ModelAndView profile(@PathVariable(name = "user_mail")String user_mail){
        ModelAndView mav = new ModelAndView("profile/editProfile");

//        INNER JOIN HER?????
        User user = userRepo.getOneUser(user_mail);
        Organization org = orgRep.getOneOrgWId(user.getFk_orgId());
        Profile profile = profileRepo.getOneProfile(user.getUser_id());

        mav.addObject("profile", profile);
        mav.addObject("org", org);
        mav.addObject("user", user);

        System.out.println(user);

        return mav;
    }

// NEW ORGANIZATION =================

    @GetMapping("/profile/organization")
    private String newOrganization(){
        return "profile/newOrganization";
    }

// UPDATE ORGANIZATION =================


    @GetMapping("/profile/organization/{org_name}")
    public ModelAndView Organization(@PathVariable(name = "org_name")String org_name){
        ModelAndView mav = new ModelAndView("profile/editOrganization");
        Organization org = orgRep.getOneOrg(org_name);

        mav.addObject("org", org);

        return mav;
    }

//  =================================  POST ROUTES =============================

//    INSERT ORGANIZATION =============

    @PostMapping("/insert/org")
    public String postOrg(WebRequest dataFromForm,  Principal principal) {
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);

        orgRep.insertOrg(org_name, org_address, cvrParsed);

        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrg(org_name);

        userRepo.updateUserWId(user.getUser_id(), org.getOrg_id());

        return "redirect:/";
    }

//    DELETE ORGANIZATION =============

    @PostMapping("/delete/org")
    public String deleteOrg(WebRequest dataFromForm) {
        String org_id      = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        orgRep.deleteOrg(idParsed);


        return "redirect:/";
    }

    //    INVITE USER ORGANIZATION =============

    @PostMapping("/insert/org/user")
    public String inviteUserToOrg(WebRequest dataFromForm) {
        String user_mail  = (dataFromForm.getParameter("user_mail"));
        String org_id     = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        User user = userRepo.getOneUser(user_mail);

        userRepo.updateUserWId(user.getUser_id(), idParsed);

        return "redirect:/";
    }

//    UPDATE ORGANIZATION =============


    @PostMapping("/update/org")
    public String updateOrg(WebRequest dataFromForm) {
        String org_id       = (dataFromForm.getParameter("org_id"));
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);
        int idParsed = Integer.parseInt(org_id);

        orgRep.updateOrg(idParsed, org_name, org_address, cvrParsed);


        return "redirect:/";
    }

//    UPDATE PROFILE =============

    @PostMapping("/update/profile")
    public String updateProfile(WebRequest dataFromForm,  Principal principal) {
        String profile_id       = (dataFromForm.getParameter("profile_id"));
        String profile_firstname  = (dataFromForm.getParameter("profile_firstname"));
        String profile_lastname     = (dataFromForm.getParameter("profile_lastname"));
        String profile_address      = (dataFromForm.getParameter("profile_address"));
        String profile_phone      = (dataFromForm.getParameter("profile_phone"));
        String profile_country      = (dataFromForm.getParameter("profile_country"));
        String profile_zip      = (dataFromForm.getParameter("profile_zip"));
        String profile_jobTitle      = (dataFromForm.getParameter("profile_jobTitle"));

        int idParse = Integer.parseInt(profile_id);
        int phoneParse = Integer.parseInt(profile_phone);
        int zipParsed = Integer.parseInt(profile_zip);

        User user = userRepo.getOneUser(principal.getName());

        profileRepo.updateProfile(idParse,profile_firstname,profile_lastname,profile_address,phoneParse, profile_country, zipParsed, profile_jobTitle, user.getUser_id());

        return "redirect:/";
    }

    //    DELETE PROFILE ========

    @PostMapping("/delete/profile")
    public String deleteProfile(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));

        int idParsed = Integer.parseInt(user_id);

        userRepo.disableUser(idParsed);


        return "redirect:/login?logout";
    }

    //    UPDATE CREDENTIALS ========

    @PostMapping("/update/credentials")
    public String updatePassword(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));
        String user_mail      = (dataFromForm.getParameter("user_mail"));
        String user_password      = (dataFromForm.getParameter("user_password"));

        int idParsed = Integer.parseInt(user_id);

        userRepo.updateCredentials(idParsed,user_mail,user_password);


        return "redirect:/login?logout";
    }

//    //  DELETE USER
//    @RequestMapping("/projects/edit")
//    public String delete(WebRequest webRequest){
//        projectRepo.delete(project_id);
//
//        System.out.println();
//        return "/projects/edit";
//    }
}
