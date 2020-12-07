package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
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

        return "project/project";
    }
// JOBTITLE =================
    @GetMapping("/projects/create/newJobtitles/{project_id}")
    private ModelAndView newjobtitle(@PathVariable(name = "project_id")String project_id ){
        ModelAndView mav = new ModelAndView("project/newJobtitle");

        int idParsed = Integer.parseInt(project_id);

        Project project = projectRepo.getOneProject(idParsed);

        mav.addObject("project", project);

        return mav;
    }

//    CREATE PROJECT =======================

    @GetMapping("/projects/create")
    private ModelAndView createproject(Principal principal){
        ModelAndView mav = new ModelAndView("project/createProject");
//        User user = userRepo.getOneUser(principal.getName());
//        List<JobTitle> jobTitles = projectRepo.getAllJobTitlesWOrg(user.getFk_orgId());
//
//        System.out.println(jobTitles);


//        User user = userRepo.getOneUser(principal.getName());
//        Organization organization = orgRep.getOneOrgWId(user.getFk_orgId());

//        mav.addObject("jobTitlesList", jobTitles);

        return mav;
    }



//    UPDATE PROJECT ======================

    @GetMapping("/projects/edit/{id}")
    private ModelAndView project(@PathVariable(name = "id")int id){
        ModelAndView mav    = new ModelAndView("project/editProject");
        Project project     = projectRepo.getOneProject(id);
        List<GetProjectJobTitles> projectTitlesList = projectRepo.getOneProjectJobTitle(id);

        mav.addObject("jobTitlesList", projectTitlesList);
        mav.addObject("project", project);
        return mav;
    }

//  =================================  POST ROUTES =============================

    // INSERT JOBTITLE ===============================
    @PostMapping("/insert/newjobtitle")
    public String postNewTitleJob(WebRequest dataFromForm, Principal principal) {
        String jobTitle_name     = (dataFromForm.getParameter("jobTitle_name"));
        String project_id        = (dataFromForm.getParameter("project_id"));

        System.out.println(project_id);

        int idParsed = Integer.parseInt(project_id);

        User user = userRepo.getOneUser(principal.getName());
        JobTitle jobTitleCheck = jobTitleRepo.getOneJobTitleWName(jobTitle_name);

        if(jobTitleCheck == null){
            jobTitleRepo.InsertJobTitle(jobTitle_name, user.getFk_orgId());
            JobTitle jobTitle = jobTitleRepo.getOneJobTitleWName(jobTitle_name);
            System.out.println(jobTitle);
            projectRepo.insertOneProjectJobTitle(jobTitle.getJobTitle_Id(), idParsed);
        }else{
            projectRepo.insertOneProjectJobTitle(jobTitleCheck.getJobTitle_Id(), idParsed);
        }
        return "redirect:/projects/edit/"+idParsed;
    }

    //    UPDATE JOBTITLE ======================

    @PostMapping("/update/newjobtitle")
    public String updateJobTitle(WebRequest dataFromForm,  Principal principal) {
        // DataFromData objektet(WebRequest klasse) gør man kan hente data fra en form(HTML).
        String jobTitle_name = (dataFromForm.getParameter("jobTitle_name"));
        String jobTitle_id   = (dataFromForm.getParameter("jobTitle_id"));

        // grunden til jeg converter, fordi URL er ALTID er en String.
        int idParsed = Integer.parseInt(jobTitle_id);

        User user = userRepo.getOneUser(principal.getName());
        JobTitle jobTitle = jobTitleRepo.getOneJobTitleWOrgId(user.getFk_orgId());
        jobTitleRepo.updateJobTitle(jobTitle_name, idParsed);

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
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());;

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
        JobTitle jobTitle = jobTitleRepo.getOneJobTitleWOrgId(user.getFk_orgId());
        projectRepo.updateProject(idParsed, project_name, project_desc, project_duration, project_start, project_end, user.getFk_orgId());



        return "redirect:/";
    }

//    DELETE PROJECT JOB TITLE =============

    @PostMapping("/delete/projectJobTitle")
    public String deleteJobTitle(WebRequest dataFromForm) {
        String projectJobTitle_id       = (dataFromForm.getParameter("projectJobTitle_id"));
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(projectJobTitle_id);

        projectRepo.deleteProjectJobTitle(idParsed);


        return "redirect:/projects/edit/"+project_id;
    }

    //    DELETE PROJECT JOB TITLE =============

    @PostMapping("/delete/project")
    public String deleteProject(WebRequest dataFromForm) {
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(project_id);

        projectRepo.deleteProject(idParsed);


        return "redirect:/projects";
    }

}
