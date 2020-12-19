package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.repository.*;
import com.example.wearegantt.services.ObjectManager;
import com.example.wearegantt.services.ProjectServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

//    =================== SERVICES ==================

    ProjectServices projectServices = new ProjectServices();

    ObjectManager objectManager = new ObjectManager();

//   =================================================================== GET CONTROLLER ==========================================================================


// ================ PROJECTS ================

    @GetMapping("/projects")
    private String projects(Model model, Principal principal){

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        List<Project> listProjects  = objectManager.projectRepo.getAllProjects();
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        model.addAttribute("auth", authorities);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("activePage", "projects");
        model.addAttribute("org", organization);
        model.addAttribute("user", user);

        return "project/project";
    }

    //  ================  CREATE PROJECT =======================

    @GetMapping("/projects/create")
    private ModelAndView createproject(Principal principal){
        ModelAndView mav = new ModelAndView("project/createProject");

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("org", organization);
        mav.addObject("user", user);

        return mav;
    }

//   ================ UPDATE PROJECT ======================

    @GetMapping("/projects/edit/{id}")
    private ModelAndView project(@PathVariable(name = "id")int id, Principal principal){
        ModelAndView mav    = new ModelAndView("project/editProject");

        User user                                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization                   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Project project                             = objectManager.projectRepo.getOneProject(id);
        List<GetProjectJobTitles> projectTitlesList = objectManager.jobTitleRepo.getOneProjectJobTitle(id);
        Authorities authorities                     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("jobTitlesList", projectTitlesList);
        mav.addObject("project", project);
        mav.addObject("org", organization);
        mav.addObject("user", user);

        return mav;
    }

//    ******************************************* GET PROJECT GANTT *******************************************

//   ================ GANTT ======================

    @GetMapping("/gantt/{project_id}")
    private ModelAndView gantt(@PathVariable(name = "project_id") String project_id, Principal principal){
        ModelAndView mav = new ModelAndView("project/gantt");

        int idParsed    = Integer.parseInt(project_id);

        Project project             = objectManager.projectRepo.getOneProject(idParsed);
        Organization org            = objectManager.organizationRepo.getOneOrgWId(project.getFk_orgId());
        List<Task> taskList         = objectManager.projectRepo.getAllTasksWProjectId(project.getProject_name());
        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);

        mav.addObject("days", " days");
        mav.addObject("user", user);
        mav.addObject("test", "5/13");
        mav.addObject("taskList", taskList);
        mav.addObject("org", org);
        mav.addObject("project",project);

        return mav;
    }

//    ******************************************* GET PROJECT TASKS *******************************************


// ================ CREATE TASK ================

    @GetMapping("/projects/create/task/{project_id}")
    private ModelAndView tasks(@PathVariable(name = "project_id") String project_id){
        ModelAndView mav = new ModelAndView("project/createTask");

        int idParsed = Integer.parseInt(project_id);
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);

        Project project                     = objectManager.projectRepo.getOneProject(idParsed);
        User user                           = objectManager.userRepo.getOneUserWOrgId(project.getFk_orgId());
        Organization organization           = objectManager.organizationRepo.getOneOrgWId(project.getFk_orgId());
        Profile profile                     = objectManager.profileRepo.getOneProfile(user.getUser_id());
        List<GetProjectJobTitles> jobTitles = objectManager.jobTitleRepo.getOneProjectJobTitle(project.getProject_id());
        List<GanttPhases> phaseList         = objectManager.projectRepo.getAllGanttPhases();
        Authorities authorities             = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("yearNow", yearNow);
        mav.addObject("auth", authorities);
        mav.addObject("profile", profile);
        mav.addObject("space", " ");
        mav.addObject("user", user);
        mav.addObject("org", organization);
        mav.addObject("phaseList", phaseList);
        mav.addObject("jobTitles", jobTitles);
        mav.addObject("project", project);

        return mav;
    }

// ================ EDIT TASK ================

    @GetMapping("/gantt/edit/task/{task_id}")
    private ModelAndView editTask(@PathVariable(name = "task_id") String task_id){
        ModelAndView mav = new ModelAndView("project/editTask");

        int idParsed = Integer.parseInt(task_id);
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);

        Task task                           = objectManager.projectRepo.getAllTasksWId(idParsed);
        Project project                     = objectManager.projectRepo.getOneProjectWPName(task.getFk_projectName());
        Organization organization           = objectManager.organizationRepo.getOneOrgWId(project.getFk_orgId());
        User user                           = objectManager.userRepo.getOneUserWOrgId(organization.getOrg_id());
        Profile profile                     = objectManager.profileRepo.getOneProfile(user.getUser_id());
        List<GanttPhases> phaseList         = objectManager.projectRepo.getAllGanttPhases();
        List<GetProjectJobTitles> jobTitles = objectManager.jobTitleRepo.getOneProjectJobTitle(project.getProject_id());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());
        
        mav.addObject("yearNow", yearNow);
        mav.addObject("auth", authorities);
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

//    ******************************************* GET PROJECT JOBTITLES *******************************************


    // ================ JOBTITLE =================
    @GetMapping("/projects/create/newJobtitles/{project_id}")
    private ModelAndView newjobtitle(@PathVariable(name = "project_id")String project_id, Principal principal, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("project/createJobtitle");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("error", inputFlashMap.get("error"));
        }

        int idParsed = Integer.parseInt(project_id);

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Project project             = objectManager.projectRepo.getOneProject(idParsed);
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("project", project);
        mav.addObject("user", user);
        mav.addObject("organization", organization);


        return mav;
    }

//   =================================================================== POST CONTROLLER ==========================================================================


//    ******************************************* POST PROJECT ****************************************


// ================ INSERT PROJECT ====================

    @PostMapping("/insert/project")
    public String postProject(WebRequest dataFromForm, Principal principal) throws ParseException {
        String project_name     = (dataFromForm.getParameter("project_name"));
        String project_desc     = (dataFromForm.getParameter("project_desc"));
        String project_start    = (dataFromForm.getParameter("project_start"));
        String project_end      = (dataFromForm.getParameter("project_end"));

        int totalDays = projectServices.calcTotalDays2(project_start, project_end);

        User user           = objectManager.userRepo.getOneUser(principal.getName());
        Organization org    = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());

        objectManager.projectRepo.InsertProject(project_name, project_desc, totalDays, project_start, project_end, org.getOrg_id());

        return "redirect:/projects/";
    }

// ================= UPDATE PROJECT ================

    @PostMapping("/update/project") //URL'en
    public String updateProject(WebRequest dataFromForm,  Principal principal) throws ParseException {
        // DataFromData objektet(WebRequest) gør man kan hente data fra en form(HTML).
        String project_id         = (dataFromForm.getParameter("project_id"));
        String project_name       = (dataFromForm.getParameter("project_name"));
        String project_desc       = (dataFromForm.getParameter("project_desc"));
        String project_start      = (dataFromForm.getParameter("project_start"));
        String project_end        = (dataFromForm.getParameter("project_end"));

        int totalDays = projectServices.calcTotalDays2(project_start, project_end);

        int idParsed = Integer.parseInt(project_id);

        User user = objectManager.userRepo.getOneUser(principal.getName());
        objectManager.projectRepo.updateProject(idParsed, project_name, project_desc, totalDays, project_start, project_end, user.getFk_orgId());

        return "redirect:/projects/";
    }

    //  ==============  DELETE PROJECT =============

    @PostMapping("/delete/project")
    public String deleteProject(WebRequest dataFromForm) {
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(project_id);

        objectManager.projectRepo.deleteProject(idParsed);

        return "redirect:/projects";
    }


//    ******************************************* POST PROJECT JOBTITLE ****************************************


// ================= INSERT JOBTITLE ===============

    @PostMapping("/insert/newjobtitle")
    public RedirectView postNewTitleJob(RedirectAttributes redirectAttributes, WebRequest dataFromForm, Principal principal) {
        String jobTitle_name     = (dataFromForm.getParameter("jobTitle_name"));
        String project_id        = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(project_id);
        boolean jobTitleExists = false;

        User user               = objectManager.userRepo.getOneUser(principal.getName());
        JobTitle jobTitleCheck  = objectManager.jobTitleRepo.getOneJobTitleWName(jobTitle_name);

        // IF JOB TITLE EXISTS IN DB VALIDATE IF EXISTS IN PROJECT
        if(jobTitleCheck != null){
            jobTitleExists  = objectManager.jobTitleRepo.checkJobTitleExists(jobTitleCheck.getJobTitle_Id());
        }

        // IF JOBTITLE DOESN'T EXISTS AT ALL - INSERT NEW JOBTITLE TO PROJECT + DB
        if(jobTitleCheck == null){
            objectManager.jobTitleRepo.InsertJobTitle(jobTitle_name, user.getFk_orgId());
            JobTitle jobTitle = objectManager.jobTitleRepo.getOneJobTitleWName(jobTitle_name);
            System.out.println(jobTitle);
            objectManager.jobTitleRepo.insertOneProjectJobTitle(jobTitle.getJobTitle_Id(), idParsed);

        // IF JOB TITLE EXIST IN DB - INSERT JOB TITLE TO PROJECT TABLE
        }else{
            if(jobTitleExists == true){

                // IF JOB TITLE EXISTS IN DB AND PROJECT TABLE - RETURN "Job Title Already Exists"
                redirectAttributes.addFlashAttribute("error", "Job Title Already Exists");
                return new RedirectView("/projects/create/newJobtitles/"+project_id);
            }
            objectManager.jobTitleRepo.insertOneProjectJobTitle(jobTitleCheck.getJobTitle_Id(), idParsed);
        }
        return new RedirectView ("/projects/edit/"+project_id);
    }

//   ====================== UPDATE JOBTITLE ======================

    @PostMapping("/update/newjobtitle")
    public String updateJobTitle(WebRequest dataFromForm) {
        String jobTitle_name = (dataFromForm.getParameter("jobTitle_name"));
        String jobTitle_id   = (dataFromForm.getParameter("jobTitle_id"));

        int idParsed = Integer.parseInt(jobTitle_id);

        objectManager.jobTitleRepo.updateJobTitle(jobTitle_name, idParsed);

        return "redirect:/";
    }

//  ===============  DELETE PROJECT JOB TITLE =============

    @PostMapping("/delete/projectJobTitle")
    public String deleteJobTitle(WebRequest dataFromForm) {
        String projectJobTitle_id       = (dataFromForm.getParameter("projectJobTitle_id"));
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(projectJobTitle_id);

        objectManager.jobTitleRepo.deleteProjectJobTitle(idParsed);

        return "redirect:/projects/edit/"+project_id;
    }


//    ******************************************* POST TASKS ****************************************


// ====================== INSERT TASK =======================

    @PostMapping("/insert/newTask")
    public String postTask(WebRequest dataFromForm) throws ParseException {
        String project_name            = (dataFromForm.getParameter("project_name"));
        String task_name            = (dataFromForm.getParameter("task_name"));
        String task_description     = (dataFromForm.getParameter("task_description"));
        String task_start           = (dataFromForm.getParameter("task_start"));
        String task_end             = (dataFromForm.getParameter("task_end"));
        String profile_name           = (dataFromForm.getParameter("profile_name"));
        String ganttPhase_name          = (dataFromForm.getParameter("ganttPhase_name"));
        String jobTitle_name          = (dataFromForm.getParameter("jobTitle_name"));

        int totalDays = projectServices.calcTotalDays2(task_start, task_end);

        String processStart     = task_start.substring(5,7);
        String processEnd       = task_end.substring(5,7);
        int processStartParsed  = Integer.parseInt(processStart);
        int processEndParsed    = Integer.parseInt(processEnd);
//        int task_durationParsed = Integer.parseInt(task_duration);
        Project project = objectManager.projectRepo.getOneProjectWName(project_name);

        objectManager.projectRepo.insertTask(task_name, task_description, totalDays, task_start, task_end, processEndParsed ,processStartParsed, project_name, profile_name, ganttPhase_name, jobTitle_name);

        return "redirect:/gantt/"+project.getProject_id();
    }

// ====================== UPDATE TASK =======================

    @PostMapping("/update/newTask")
    public String updateTask(WebRequest dataFromForm) throws ParseException {
        String task_id                  = (dataFromForm.getParameter("task_id"));
        String project_name             = (dataFromForm.getParameter("project_name"));
        String task_name                = (dataFromForm.getParameter("task_name"));
        String task_description         = (dataFromForm.getParameter("task_description"));
        String task_start               = (dataFromForm.getParameter("task_start"));
        String task_end                 = (dataFromForm.getParameter("task_end"));
        String profile_name             = (dataFromForm.getParameter("profile_name"));
        String ganttPhase_name          = (dataFromForm.getParameter("ganttPhase_name"));
        String jobTitle_name            = (dataFromForm.getParameter("jobTitle_name"));

        int totalDays = projectServices.calcTotalDays2(task_start, task_end);

        String processStart     = task_start.substring(5,7);
        String processEnd       = task_end.substring(5,7);
        int taskIdParsed        = Integer.parseInt(task_id);
        int processStartParsed  = Integer.parseInt(processStart);
        int processEndParsed    = Integer.parseInt(processEnd);
        Project project = objectManager.projectRepo.getOneProjectWName(project_name);

        objectManager.projectRepo.updateTask(taskIdParsed, task_name, task_description, totalDays, task_start, task_end, processEndParsed ,processStartParsed, project_name, profile_name, ganttPhase_name, jobTitle_name);

        return "redirect:/gantt/"+project.getProject_id();
    }

//  ======================  DELETE TASK ======================

    @PostMapping("/delete/task")
    public String deletetask(WebRequest dataFromForm) {
        String task_id       = (dataFromForm.getParameter("task_id"));
        String project_id    = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(task_id);

        objectManager.projectRepo.deleteTask(idParsed);

        return "redirect:/gantt/"+project_id;
    }

}
