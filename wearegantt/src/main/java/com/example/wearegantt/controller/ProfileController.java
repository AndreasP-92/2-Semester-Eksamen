package com.example.wearegantt.controller;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.OrganizationRepo;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.ProjectRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    //REPOSITORIES ====================

    OrganizationRepo orgRep = new OrganizationRepo();


//    GET ROUTES ==================

    @GetMapping("/editprofile")
    private String profile(){
        return "login/editprofile";
    }


    @GetMapping("/gantt")
    private String gantt(){
        return "profile/gantt";
    }

    @GetMapping("/projects")
    private String projects(){
//      udskriv alle projekter her!!
//      Og send ojektet til html her

        return "profile/project";
    }


    @GetMapping("/projects/{id}")
    private ModelAndView projects(@PathVariable(name = "id")int id){
        ModelAndView mav = new ModelAndView("profile/project");
//        Project project = ProjectRepo.getOneProject(id);
//        mav.addObject("profile", project);
        return mav;
    }

//    @GetMapping("/profile/organization/{org_id}")
//    public ModelAndView organization(@PathVariable(name = "org_id")int org_id){
//        ModelAndView mav = new ModelAndView("profile/organization");
//        Organization org = orgRep.getOneOrg(org_id);
//
//        mav.addObject("org", org);
//
//        System.out.println(org);
//        //      udskriv alle projekter her!!
////      Og send ojektet til html her
//
//        return mav;
//    }

//    POST ROUTES ==================

}
