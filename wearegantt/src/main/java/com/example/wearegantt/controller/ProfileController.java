package com.example.wearegantt.controller;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.JobTitleRepo;
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

    JobTitleRepo jobTitleRepo = new JobTitleRepo();


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
        ModelAndView mav = new ModelAndView("editProfile");

//        INNER JOIN HER?????
        User user = userRepo.getOneUser(user_mail);
        Organization org = orgRep.getOneOrgWId(user.getFk_orgId());

        mav.addObject("org", org);
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



        return "profile/project";
    }

    @GetMapping("/projects/create/{id}")
    private ModelAndView createproject(@PathVariable(name = "id") int id, Principal principal){
        ModelAndView mav = new ModelAndView("profile/createProject");

        User user = userRepo.getOneUser(principal.getName());

        Organization organization = orgRep.getOneOrgWId(user.getFk_orgId());

        mav.addObject("organization", organization);

        return mav;
    }

    @GetMapping("/projects/opret")
    private String opretproject(){

        return "createProject";
    }

    @GetMapping("/projects/edit/{id}")
    private ModelAndView project(@PathVariable(name = "id")int id){
        ModelAndView mav = new ModelAndView("profile/editProject");
        Project project = projectRepo.getOneProject(id);
        mav.addObject("project", project);
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

//    POST ROUTES ==================

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
// INSERT PROJECT

    @PostMapping("/insert/project")
    public String postProject(WebRequest dataFromForm, Principal principal) {
        String project_name     = (dataFromForm.getParameter("project_name"));
        String project_desc     = (dataFromForm.getParameter("project_desc"));
        String project_duration = (dataFromForm.getParameter("project_duration"));
        String project_start    = (dataFromForm.getParameter("project_start"));
        String project_end      = (dataFromForm.getParameter("project_end"));




        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());
        JobTitle jobTitle   = jobTitleRepo.getOneJobTitle(user.getFk_orgId());

        projectRepo.InsertProject(project_name, project_desc, project_duration, project_start, project_end, org.getOrg_id(), jobTitle.getJobTitle_Id());

//        userRepo.updateUserWId(user.getUser_id(), org.getOrg_id());

        return "redirect:/";
    }

    // UPDATE PROJECT
    @PostMapping("/update/project") //URL'en
    public String updateProject(WebRequest dataFromForm,  Principal principal) {
        // DataFromData objektet(WebRequest) gør man kan hente data fra en form(HTML).
        String project_id         = (dataFromForm.getParameter("project_id"));
        String project_name       = (dataFromForm.getParameter("project_name"));
        String project_desc       = (dataFromForm.getParameter("project_desc"));
        String project_duration   = (dataFromForm.getParameter("project_duration"));
        String project_start      = (dataFromForm.getParameter("project_start"));
        String project_end        = (dataFromForm.getParameter("project_end"));

        // grunden til jeg converter, fordi URL er ALTID er en String.
        int idParsed = Integer.parseInt(project_id);


        User user = userRepo.getOneUser(principal.getName());
        JobTitle jobTitle = jobTitleRepo.getOneJobTitle(user.getFk_orgId());
        projectRepo.updateProject(idParsed, project_name, project_desc, project_duration, project_start, project_end, user.getFk_orgId(), jobTitle.getJobTitle_Id());



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
