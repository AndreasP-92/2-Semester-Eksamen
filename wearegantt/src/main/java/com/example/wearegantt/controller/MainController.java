package com.example.wearegantt.controller;

import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@org.springframework.stereotype.Controller
public class MainController {


//    =================================== GET ROUTES ==================

    @GetMapping("/")
    private String index(){

        return "main/index";
    }

    // CONTACT ==========

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

    // NEWSFEED ==========

    @GetMapping("/newsfeed")
    private String newsfeed(){
        //        List<Newsfeeds> listNewsfeeds = newsfeedRepo.getAllNewsfeeds();
//        model.addAttribute("listNewsfeeds", listNewsfeeds);

//        List<Newsfeeds> listNewsfeeds = newsfeedRepo.getAllNewsfeeds();
//        model.addAttribute("listNewsfeeds", listNewsfeeds);


        return "main/newsfeed";
    }


//    POST ROUTES ==================
@GetMapping("/newsfeed/create")
private ModelAndView createnewsfeed(Principal principal){
    ModelAndView mav = new ModelAndView("main/createNewsfeed");
//        User user = userRepo.getOneUser(principal.getName());
//        List<JobTitle> jobTitles = projectRepo.getAllJobTitlesWOrg(user.getFk_orgId());
//
//        System.out.println(jobTitles);

//    ===================================  POST ROUTES ==================

//        User user = userRepo.getOneUser(principal.getName());
//        Organization organization = orgRep.getOneOrgWId(user.getFk_orgId());

//        mav.addObject("jobTitlesList", jobTitles);

    return mav;
}
}
