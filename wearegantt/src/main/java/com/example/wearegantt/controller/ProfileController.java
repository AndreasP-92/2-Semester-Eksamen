package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {

    //REPOSITORIES ====================

    OrganizationRepo orgRep = new OrganizationRepo();

    ProjectRepo projectRepo = new ProjectRepo();

    UserRepo userRepo = new UserRepo();

    ProfileRepo profileRepo = new ProfileRepo();

    JobTitleRepo jobTitleRepo = new JobTitleRepo();


//    GET ROUTES ==================

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



    @GetMapping("/profile/organization")
    private String newOrganization(){
        return "profile/newOrganization";
    }

    @GetMapping("/profile/organization/{org_name}")
    public ModelAndView Organization(@PathVariable(name = "org_name")String org_name, Principal principal){
        ModelAndView mav = new ModelAndView("profile/editOrganization");
        Organization org = orgRep.getOneOrg(org_name);

        mav.addObject("org", org);

        return mav;
    }

//  =================================  POST ROUTES =============================

    @PostMapping("/insert/org")
    public String postOrg(WebRequest dataFromForm,  Principal principal) {
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);
        int fk_orgId = 0;

        orgRep.insertOrg(org_name, org_address, cvrParsed);

        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrg(org_name);

        userRepo.updateUserWId(user.getUser_id(), org.getOrg_id());

        return "redirect:/";
    }

    @PostMapping("/delete/org")
    public String deleteOrg(WebRequest dataFromForm) {
        String org_id      = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);
        System.out.println(idParsed);

        orgRep.deleteOrg(idParsed);


        return "redirect:/";
    }

    @PostMapping("/insert/org/user")
    public String insertUserToOrg(WebRequest dataFromForm,  Principal principal) {
        String user_mail  = (dataFromForm.getParameter("user_mail"));
        String org_id     = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        System.out.println(user_mail + org_id);
//
        User user = userRepo.getOneUser(user_mail);
//
        userRepo.updateUserWId(user.getUser_id(), idParsed);

        return "redirect:/";
    }



    @PostMapping("/update/org")
    public String updateOrg(WebRequest dataFromForm,  Principal principal) {
        String org_id       = (dataFromForm.getParameter("org_id"));
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);
        int idParsed = Integer.parseInt(org_id);

        orgRep.updateOrg(idParsed, org_name, org_address, cvrParsed);


        return "redirect:/";
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
