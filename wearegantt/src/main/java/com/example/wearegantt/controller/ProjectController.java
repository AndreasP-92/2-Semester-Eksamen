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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

//REPOSITORIES ====================

    OrganizationRepo orgRep = new OrganizationRepo();

    ProjectRepo projectRepo = new ProjectRepo();

    UserRepo userRepo = new UserRepo();

    JobTitleRepo jobTitleRepo = new JobTitleRepo();

    ProfileRepo profileRepo = new ProfileRepo();

// ================================================== GANTT =====================================

    @GetMapping("/gantt/{project_id}")
    private ModelAndView gantt(@PathVariable(name = "project_id") String project_id, Principal principal){
        ModelAndView mav = new ModelAndView("project/gantt");

        int idParsed    = Integer.parseInt(project_id);

        Project project             = projectRepo.getOneProject(idParsed);
        Organization org            = orgRep.getOneOrgWId(project.getFk_orgId());
        List<Task> taskList         = projectRepo.getAllTasksWProjectId(project.getProject_name());
        User user                   = userRepo.getOneUser(principal.getName());



        System.out.println(project);

        System.out.println(taskList);

        System.out.println(org.getOrg_name());
        mav.addObject("user", user);
        mav.addObject("test", "5/13");
        mav.addObject("taskList", taskList);
        mav.addObject("org", org);
        mav.addObject("project",project);

        return mav;
    }

// CREATE TASK ================

    @GetMapping("/projects/create/task/{project_id}")
    private ModelAndView tasks(@PathVariable(name = "project_id") String project_id){
        ModelAndView mav = new ModelAndView("project/createTask");

        int idParsed = Integer.parseInt(project_id);

        Project project                     = projectRepo.getOneProject(idParsed);
        User user                           = userRepo.getOneUserWOrgId(project.getFk_orgId());
        Organization organization           = orgRep.getOneOrgWId(project.getFk_orgId());
        Profile profile                     = profileRepo.getOneProfile(user.getUser_id());
        List<GetProjectJobTitles> jobTitles = jobTitleRepo.getOneProjectJobTitle(project.getProject_id());
        List<GanttPhases> phaseList         = projectRepo.getAllGanttPhases();

        System.out.println("projects=======" + project);
        System.out.println("jobTitles=======" + jobTitles);
        System.out.println("user=======" + user);
        System.out.println("profiel=======" + profile);

        mav.addObject("profile", profile);
        mav.addObject("space", " ");
        mav.addObject("user", user);
        mav.addObject("org", organization);
        mav.addObject("phaseList", phaseList);
        mav.addObject("jobTitles", jobTitles);
        mav.addObject("project", project);

        return mav;
    }

    // EDIT TASK ================

    @GetMapping("/gantt/edit/task/{task_id}")
    private ModelAndView editTask(@PathVariable(name = "task_id") String task_id){
        ModelAndView mav = new ModelAndView("project/editTask");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");


        int idParsed = Integer.parseInt(task_id);

        Task task                           = projectRepo.getAllTasksWId(idParsed);
        Project project                     = projectRepo.getOneProjectWPName(task.getFk_projectName());
        Organization organization           = orgRep.getOneOrgWId(project.getFk_orgId());
        User user                           = userRepo.getOneUserWOrgId(organization.getOrg_id());
        Profile profile                     = profileRepo.getOneProfile(user.getUser_id());
        List<GanttPhases> phaseList         = projectRepo.getAllGanttPhases();
        List<GetProjectJobTitles> jobTitles = jobTitleRepo.getOneProjectJobTitle(project.getProject_id());

        System.out.println("projects=======" + task);

//        mav.addObject("timeStart", timeStart);
        mav.addObject("profile", profile);
        mav.addObject("space", " ");
        mav.addObject("user", user);
        mav.addObject("org", organization);
        mav.addObject("phaseList", phaseList);
        mav.addObject("jobTitles", jobTitles);
        mav.addObject("project", project);
        mav.addObject("task", task);


        return mav;
    }

// PROJECTS ================

    @GetMapping("/projects")
    private String projects(Model model, Principal principal){

        User user                   = userRepo.getOneUser(principal.getName());
        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());
        List<Project> listProjects  = projectRepo.getAllProjects();

        model.addAttribute("listProjects", listProjects);
        model.addAttribute("activePage", "projects");
        model.addAttribute("org", organization);
        model.addAttribute("user", user);

        return "project/project";
    }
// JOBTITLE =================
    @GetMapping("/projects/create/newJobtitles/{project_id}")
    private ModelAndView newjobtitle(@PathVariable(name = "project_id")String project_id, Principal principal){
        ModelAndView mav = new ModelAndView("project/newJobtitle");

        int idParsed = Integer.parseInt(project_id);

        User user                   = userRepo.getOneUser(principal.getName());
        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());
        Project project             = projectRepo.getOneProject(idParsed);

        mav.addObject("project", project);
        mav.addObject("user", user);
        mav.addObject("organization", organization);


        return mav;
    }

//    CREATE PROJECT =======================

    @GetMapping("/projects/create")
    private ModelAndView createproject(Principal principal){
        ModelAndView mav = new ModelAndView("project/createProject");



        User user                   = userRepo.getOneUser(principal.getName());
        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());

        mav.addObject("org", organization);
        mav.addObject("user", user);


        return mav;
    }



//    UPDATE PROJECT ======================

    @GetMapping("/projects/edit/{id}")
    private ModelAndView project(@PathVariable(name = "id")int id, Principal principal){
        ModelAndView mav    = new ModelAndView("project/editProject");

        User user                   = userRepo.getOneUser(principal.getName());
        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());
        Project project             = projectRepo.getOneProject(id);
        List<GetProjectJobTitles> projectTitlesList = jobTitleRepo.getOneProjectJobTitle(id);

        mav.addObject("jobTitlesList", projectTitlesList);
        mav.addObject("project", project);
        mav.addObject("org", organization);
        mav.addObject("user", user);

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

        User user               = userRepo.getOneUser(principal.getName());
        JobTitle jobTitleCheck  = jobTitleRepo.getOneJobTitleWName(jobTitle_name);

        if(jobTitleCheck == null){
            jobTitleRepo.InsertJobTitle(jobTitle_name, user.getFk_orgId());
            JobTitle jobTitle = jobTitleRepo.getOneJobTitleWName(jobTitle_name);
            System.out.println(jobTitle);
            jobTitleRepo.insertOneProjectJobTitle(jobTitle.getJobTitle_Id(), idParsed);
        }else{
            jobTitleRepo.insertOneProjectJobTitle(jobTitleCheck.getJobTitle_Id(), idParsed);
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

//        User user = userRepo.getOneUser(principal.getName());
//        JobTitle jobTitle = jobTitleRepo.getOneJobTitleWOrgId(user.getFk_orgId());
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
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());

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
//        JobTitle jobTitle = jobTitleRepo.getOneJobTitleWOrgId(user.getFk_orgId());
        projectRepo.updateProject(idParsed, project_name, project_desc, project_duration, project_start, project_end, user.getFk_orgId());

        return "redirect:/";
    }

//    DELETE PROJECT JOB TITLE =============

    @PostMapping("/delete/projectJobTitle")
    public String deleteJobTitle(WebRequest dataFromForm) {
        String projectJobTitle_id       = (dataFromForm.getParameter("projectJobTitle_id"));
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(projectJobTitle_id);

        jobTitleRepo.deleteProjectJobTitle(idParsed);

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

    // INSERT TASK =======================

    @PostMapping("/insert/newTask")
    public String postTask(WebRequest dataFromForm, Principal principal) {
        String project_name            = (dataFromForm.getParameter("project_name"));
        String task_name            = (dataFromForm.getParameter("task_name"));
        String task_description     = (dataFromForm.getParameter("task_description"));
        String task_duration        = (dataFromForm.getParameter("task_duration"));
        String task_start           = (dataFromForm.getParameter("task_start"));
        String task_end             = (dataFromForm.getParameter("task_end"));
        String profile_name           = (dataFromForm.getParameter("profile_name"));
        String ganttPhase_name          = (dataFromForm.getParameter("ganttPhase_name"));
        String jobTitle_name          = (dataFromForm.getParameter("jobTitle_name"));



        String processStart     = task_start.substring(5,7);
        String processEnd       = task_end.substring(5,7);
        int processStartParsed  = Integer.parseInt(processStart);
        int processEndParsed    = Integer.parseInt(processEnd);
        int task_durationParsed = Integer.parseInt(task_duration);
//        int gantt_phaseParsed   = Integer.parseInt(gantt_phase);
//        int project_idParsed    = Integer.parseInt(project_id);
//        int jobTitle_idParsed    = Integer.parseInt(jobTitle_id);
//        int profile_idParsed    = Integer.parseInt(profile_id);
        Project project = projectRepo.getOneProjectWName(project_name);

//        System.out.println("job title========"+jobTitle_idParsed);


//        User user = userRepo.getOneUser(dataFromForm.getRemoteUser());
//        Profile profile = profileRepo.getOneProfile(user.getUser_id());

        projectRepo.insertTask(task_name, task_description, task_durationParsed, task_start, task_end, processEndParsed ,processStartParsed, project_name, profile_name, ganttPhase_name, jobTitle_name);

        return "redirect:/gantt/"+project.getProject_id();
    }

// UPDATE TASK =======================

    @PostMapping("/update/newTask")
    public String updateTask(WebRequest dataFromForm, Principal principal) {
        String task_id                  = (dataFromForm.getParameter("task_id"));
        String project_name             = (dataFromForm.getParameter("project_name"));
        String task_name                = (dataFromForm.getParameter("task_name"));
        String task_description         = (dataFromForm.getParameter("task_description"));
        String task_duration            = (dataFromForm.getParameter("task_duration"));
        String task_start               = (dataFromForm.getParameter("task_start"));
        String task_end                 = (dataFromForm.getParameter("task_end"));
        String profile_name             = (dataFromForm.getParameter("profile_name"));
        String ganttPhase_name          = (dataFromForm.getParameter("ganttPhase_name"));
        String jobTitle_name            = (dataFromForm.getParameter("jobTitle_name"));

        System.out.println("PROFILE NAME ========================"+profile_name);


        String processStart     = task_start.substring(5,7);
        String processEnd       = task_end.substring(5,7);
        int taskIdParsed        = Integer.parseInt(task_id);
        int processStartParsed  = Integer.parseInt(processStart);
        int processEndParsed    = Integer.parseInt(processEnd);
        int task_durationParsed = Integer.parseInt(task_duration);
//        int gantt_phaseParsed   = Integer.parseInt(gantt_phase);
//        int project_idParsed    = Integer.parseInt(project_id);
//        int jobTitle_idParsed    = Integer.parseInt(jobTitle_id);
//        int profile_idParsed    = Integer.parseInt(profile_id);
        Project project = projectRepo.getOneProjectWName(project_name);

//        System.out.println("job title========"+jobTitle_idParsed);


//        User user = userRepo.getOneUser(dataFromForm.getRemoteUser());
//        Profile profile = profileRepo.getOneProfile(user.getUser_id());

        projectRepo.updateTask(taskIdParsed, task_name, task_description, task_durationParsed, task_start, task_end, processEndParsed ,processStartParsed, project_name, profile_name, ganttPhase_name, jobTitle_name);

        return "redirect:/gantt/"+project.getProject_id();
    }

}
