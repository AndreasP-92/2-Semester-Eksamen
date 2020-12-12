package com.example.wearegantt.controller;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.OrganizationRepo;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.ProjectRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller

public class AdminController {

    OrganizationRepo orgRep = new OrganizationRepo();

    ProjectRepo projectRepo = new ProjectRepo();

    ProfileRepo profileRepo = new ProfileRepo();

    UserRepo userRepo = new UserRepo();

//    GET ROUTES ==================
    // Admin Index

    @GetMapping("/admin")
    public String admin(){
        return "admin/adminIndex";
    }


// EDIT Admin PROFILE =================


    @GetMapping("/admin/edit/profile/{user_mail}")
    private ModelAndView profile(@PathVariable(name = "user_mail")String user_mail){
        ModelAndView mav = new ModelAndView("admin/adminEditProfile");

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


//========================================= POST ROUTES ========================================================
//    SAVE USER ==============

    @PostMapping("/admin/insert/user")
    public String postUser(WebRequest dataFromForm) {
        String firstname = (dataFromForm.getParameter("firstname"));
        String lastname = (dataFromForm.getParameter("lastname"));
        String address = (dataFromForm.getParameter("address"));
        String phone = (dataFromForm.getParameter("phone"));
        String country = (dataFromForm.getParameter("country"));
        String zipcode = (dataFromForm.getParameter("zipcode"));
        String jobTitle = (dataFromForm.getParameter("jobTitle"));
        String password = (dataFromForm.getParameter("password"));
        String email = (dataFromForm.getParameter("email"));
        String ADMIN_ROLE = (dataFromForm.getParameter("ADMIN_ROLE"));


        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        userRepo.insertUser(email, password, 1);
        User userObj = userRepo.getOneUser(email);

        System.out.println(userObj);

        userRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());

        userRepo.insertAuthUser("ADMIN_ROLE", userObj.getUser_mail());


        return "redirect:/";
    }

    @PostMapping("/admin/insert/project")
    public String postAdminProject(WebRequest dataFromForm, Principal principal) {
        String project_name     = (dataFromForm.getParameter("project_name"));
        String project_desc     = (dataFromForm.getParameter("project_desc"));
        String project_duration = (dataFromForm.getParameter("project_duration"));
        String project_start    = (dataFromForm.getParameter("project_start"));
        String project_end      = (dataFromForm.getParameter("project_end"));

        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());

        projectRepo.InsertProject(project_name, project_desc, project_duration, project_start, project_end, org.getOrg_id());

        return "redirect:/";
    }
}
