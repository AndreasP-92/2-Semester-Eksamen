package com.example.wearegantt.controller;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.OrganizationRepo;
import com.example.wearegantt.repository.ProjectRepo;
import com.example.wearegantt.repository.UserRepo;
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


//    GET ROUTES ==================

//    @GetMapping("/profile/{profile_mail}")
//    public ModelAndView profile(@PathVariable(name = "profile_mail") String profile_mail){
//        ModelAndView mav = new ModelAndView("user/profile");
//        Profile profile = daoProfile.get(profile_mail);
//        mav.addObject("profile", profile);
//
//        return mav;
//    }

    @GetMapping("/editprofile/{user_mail}")
    private ModelAndView profile(@PathVariable(name = "user_mail")String user_mail){
        ModelAndView mav = new ModelAndView("profile/editprofile");
        User user = userRepo.getOneUser(user_mail);

        mav.addObject("user", user);
        System.out.println(user);

        return mav;
    }


    @GetMapping("/gantt")
    private String gantt(){
        return "profile/gantt";
    }

    @GetMapping("/projects")
    private String projects(Model model){


        List<Project> listProjects = projectRepo.getAllProjects();
        model.addAttribute("listProjects", listProjects);

        System.out.println(listProjects);

        return "profile/project";
    }


    @GetMapping("/projects/{id}")
    private ModelAndView project(@PathVariable(name = "id")int id){
        ModelAndView mav = new ModelAndView("profile/project");
//        Project project = ProjectRepo.getOneProject(id);
//        mav.addObject("profile", project);
        return mav;
    }
    @GetMapping("/profile/organization")
    private String newOrganization(){
        return "profile/newOrganization";
    }

    @GetMapping("/profile/organization/{org_id}")
    public ModelAndView Organization(@PathVariable(name = "org_id")int org_id, Principal principal){
        ModelAndView mav = new ModelAndView("profile/editOrganization");
        Organization org = orgRep.getOneOrg(org_id);

        mav.addObject("org", org);

        return mav;
    }

//    POST ROUTES ==================

    @PostMapping("/insert/org")
    public String postOrg(WebRequest dataFromForm,  Principal principal) {
        System.out.println("test");
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);

        orgRep.insertOrg(org_name, org_address, cvrParsed);

        User user = userRepo.getOneUser(principal.getName());

        userRepo.updateUserOrg(user.getUser_id());




        return "redirect:/";
    }

}
