package com.example.wearegantt.controller;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.OrganizationRepo;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.ProjectRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller

public class AdminController {

    OrganizationRepo orgRep = new OrganizationRepo();

    ProjectRepo projectRepo = new ProjectRepo();

    ProfileRepo profileRepo = new ProfileRepo();

    UserRepo userRepo = new UserRepo();

//    GET ROUTES ==================
    // Admin Index

    @GetMapping("/admin")
    public String admin() {
        return "admin/adminIndex";
    }


    // Admin Look Up User
    @GetMapping("/admin/lookupuser")
    public String adminLookUpUsers(Model model) {

        List<User> userlist = userRepo.getAllUsers();
        model.addAttribute("userlist", userlist);

        return "/admin/adminLookUpUser";
    }

    // Admin EDIT PROFILE =================
    @GetMapping("/admin/editprofile/{user_id}")
    public ModelAndView adminEditProfile(@PathVariable(name = "user_id") int user_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditProfile");

        User user = userRepo.getOneUserWId(user_id);

        Profile profile = profileRepo.getOneProfile(user_id);



        mav.addObject("profile", profile);

        mav.addObject("user", user);

        return mav;
    }

    // Admin Support
    @GetMapping("/admin/support")
    public String adminSupport() {
        return "admin/adminSupport";
    }

    //Admin Chat
    @GetMapping("/admin/chat")
    public String adminChat() {
        return "admin/adminChat";
    }


    @GetMapping("/admin/createuser")
    public String adminCreateUser() {
        return "admin/adminCreateUser";
    }


    // Admin Look Up Organization
    @GetMapping("/admin/lookuporganization")
    public String adminLookUpOrganizations(Model model) {

        List<Organization> orglist = orgRep.getAllOrgs();
        model.addAttribute("orglist", orglist);

        return "/admin/adminLookUpOrganization";
    }


    //Admin Edit Organisation
    @GetMapping("/admin/editorganizations/{org_id}")
    public ModelAndView adminEditOrganization(@PathVariable(name = "org_id") int org_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditOrganizations");

        Organization org = orgRep.getOneOrgWId(org_id);

        mav.addObject("org", org);

        return mav;

    }

    //Admin Projects
    @GetMapping("/admin/lookupproject")
    public String adminLookUpProjects(Model model){

        List<Project> projectList = projectRepo.getAllProjects();
        model.addAttribute("projectList", projectList);

        return "admin/adminLookUpProject";
    }

    //Admin Edit Projects
    @GetMapping("/admin/editprojects/{project_id}")
    public ModelAndView adminEditProject(@PathVariable(name = "project_id") int project_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditProjects");

        Organization org = orgRep.getOneOrgWId(project_id);

        mav.addObject("org", org);

        return mav;
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
        String role = (dataFromForm.getParameter("role"));


        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        userRepo.insertUser(email, password, 1);
        User userObj = userRepo.getOneUser(email);

        System.out.println(userObj);

        userRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());

        userRepo.insertAuthUser(role, userObj.getUser_mail());


        return "redirect:/";
    }

    //================ Update User
    @PostMapping("/admin/update/user")
    public String updateUser(WebRequest dataFromForm,  Principal principal) {
        String profile_id           = (dataFromForm.getParameter("profile_id"));
        String profile_firstname    = (dataFromForm.getParameter("profile_firstname"));
        String profile_lastname     = (dataFromForm.getParameter("profile_lastname"));
        String profile_address      = (dataFromForm.getParameter("profile_address"));
        String profile_phone        = (dataFromForm.getParameter("profile_phone"));
        String profile_country      = (dataFromForm.getParameter("profile_country"));
        String profile_zip          = (dataFromForm.getParameter("profile_zip"));
        String profile_jobTitle     = (dataFromForm.getParameter("profile_jobTitle"));


        int idParse     = Integer.parseInt(profile_id);
        int phoneParse  = Integer.parseInt(profile_phone);
        int zipParsed   = Integer.parseInt(profile_zip);

        User user = userRepo.getOneUser(principal.getName());

        profileRepo.updateProfile(idParse,profile_firstname,profile_lastname,profile_address,phoneParse, profile_country, zipParsed, profile_jobTitle, user.getUser_id());



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

    @PostMapping("/admin/insert/organization")
    public String postAdminOrganization(WebRequest dataFromForm, Principal principal) {
        String org_name         = (dataFromForm.getParameter("org_name"));
        String org_address      = (dataFromForm.getParameter("org_address"));
        String org_cvr          = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);

        orgRep.insertOrg(org_name, org_address, cvrParsed);

        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());

        orgRep.insertOrg(org_name, org_address, cvrParsed);

        return "redirect:/";
    }
}
