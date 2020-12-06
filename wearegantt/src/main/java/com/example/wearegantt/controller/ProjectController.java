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
public class ProjectController {

    //REPOSITORIES ====================

    OrganizationRepo orgRep = new OrganizationRepo();

    ProjectRepo projectRepo = new ProjectRepo();

    UserRepo userRepo = new UserRepo();

    JobTitleRepo jobTitleRepo = new JobTitleRepo();

// GANTT =================

    @GetMapping("/gantt")
    private String gantt(){
        return "profile/gantt";
    }

// PROJECTS ================

    @GetMapping("/projects")
    private String projects(Model model){

        List<Project> listProjects = projectRepo.getAllProjects();
        model.addAttribute("listProjects", listProjects);

        return "profile/project";
    }
// JOBTITLE =================
    @GetMapping("/newjobtitle")
    private String newjobtitle(){
        return "profile/newjobtitle";
    }

//    CREATE PROJECT =======================

    @GetMapping("/projects/create")
    private ModelAndView createproject(Principal principal){
        ModelAndView mav = new ModelAndView("profile/createProject");

//        User user = userRepo.getOneUser(principal.getName());
//        Organization organization = orgRep.getOneOrgWId(user.getFk_orgId());

//        mav.addObject("organization", organization);

        return mav;
    }



//    UPDATE PROJECT ======================

    @GetMapping("/projects/edit/{id}")
    private ModelAndView project(@PathVariable(name = "id")int id){
        ModelAndView mav = new ModelAndView("profile/editProject");
        Project project = projectRepo.getOneProject(id);
        mav.addObject("project", project);
        return mav;
    }

//  =================================  POST ROUTES =============================

    // INSERT JOBTITLE ===============================
    @PostMapping("/insert/newtitlejob")
    public String postNewTitleJob(WebRequest dataFromForm, Principal principal) {
        String project_name     = (dataFromForm.getParameter("project_name"));

        return "redirect:/";
    }


// INSERT PROJECT =======================

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

        projectRepo.InsertProject(project_name, project_desc, project_duration, project_start, project_end, org.getOrg_id());

        return "redirect:/";
    }

// UPDATE PROJECT =======================

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
        projectRepo.updateProject(idParsed, project_name, project_desc, project_duration, project_start, project_end, user.getFk_orgId());



        return "redirect:/";
    }



}
