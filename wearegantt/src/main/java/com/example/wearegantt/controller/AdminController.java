package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.services.ObjectManager;
import com.example.wearegantt.services.ProjectServices;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller

public class AdminController {


//    =================== SERVICES ==================

    ProjectServices projectServices = new ProjectServices();

    ObjectManager objectManager = new ObjectManager();

//   =================================================================== GET CONTROLLER ==========================================================================

// =============== Admin Index ===============

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Profile profile             = objectManager.profileRepo.getOneProfile(user.getUser_id());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());

        model.addAttribute("organization", organization);
        model.addAttribute("profile", profile);

        return "admin/adminIndex";
    }


//    ******************************************* GET ADMIN USER *******************************************


// =============== LOOK UP USER ===============

    @GetMapping("/admin/lookupuser")
    public String adminLookUpUsers(Model model) {

        List<User> userlist = objectManager.userRepo.getAllUsers();

        model.addAttribute("userlist", userlist);

        return "/admin/adminLookUpUser";
    }

// =============== CREATE USER ===============

    @GetMapping("/admin/createuser")
        public String adminCreateUser(Model model) {
        List<AuthorityCheck> authCheck = objectManager.userRepo.getAllAuthorities();

        model.addAttribute("authCheck", authCheck);

        return "admin/adminCreateUser";
    }


// =============== EDIT USER =================

    @GetMapping("/admin/user/{user_id}")
    public ModelAndView adminEditProfile(@PathVariable(name = "user_id") int user_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditUser");

        User user                       = objectManager.userRepo.getOneUserWId(user_id);
        Profile profile                 = objectManager.profileRepo.getOneProfile(user_id);
        List<AuthorityCheck> authCheck  = objectManager.userRepo.getAllAuthorities();
        Authorities authorities         = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("authCheck", authCheck);
        mav.addObject("profile", profile);
        mav.addObject("user", user);

        return mav;
    }


//    ******************************************* GET ADMIN SUPPORT *******************************************


// =============== ALL SUPPORT TICKETS =============

    @GetMapping("/admin/support/all")
    public String adminSupport(Model model, Principal principal) {

        List<SupportTicket> supportTicketList   = objectManager.ticketRepo.getAllTickets();
        User user                               = objectManager.userRepo.getOneUser(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("supportTicketList", supportTicketList);

        return "admin/adminAllTickets";
    }

// =============== ADMIN SUPPORT ASSIGNED TICKETS =============

    @GetMapping("/admin/support/assigned/{user_id}")
    public ModelAndView assignedTickets(@PathVariable(name = "user_id")int user_id, Principal principal) {
        ModelAndView mav = new ModelAndView("admin/adminAssignedTickets");

        List<GetTicketUser> supportTicketList   = objectManager.ticketRepo.getAllUserTickets(user_id);
        User user                               = objectManager.userRepo.getOneUser(principal.getName());

        mav.addObject("supportTicketList", supportTicketList);
        mav.addObject("user", user);

        return mav;
    }

// =============== ADMIN CHAT ===============

    @GetMapping("/admin/support/chat/{ticket_id}")
    public ModelAndView adminChat(@PathVariable(name = "ticket_id")int ticket_id, Principal principal) {
        ModelAndView mav = new ModelAndView("admin/adminChat");

        List<SupportMessage> supportMessageList = objectManager.ticketRepo.getAllMessagesWTicketId(ticket_id);
        SupportTicket supportTicket             = objectManager.ticketRepo.getOneTicket(ticket_id);
        User user                               = objectManager.userRepo.getOneUser(principal.getName());
        Profile profile                         = objectManager.profileRepo.getOneProfile(user.getUser_id());

        mav.addObject("supportMessageList", supportMessageList);
        mav.addObject("supportTicket", supportTicket);
        mav.addObject("profile", profile);

        return mav;
    }

//    ******************************************* GET ADMIN ORGANIZATION *******************************************

// =============== ALL ORGANIZATIONS ===============

    @GetMapping("/admin/organization")
    public String adminLookUpOrganizations(Model model) {

        List<Organization> orglist = objectManager.organizationRepo.getAllOrgs();

        model.addAttribute("orglist", orglist);

        return "/admin/adminLookUpOrganization";
    }


// =============== GET EDIT ORGANIZATION ===============

    @GetMapping("/admin/edit/organization/{org_id}")
    public ModelAndView adminEditOrganization(@PathVariable(name = "org_id") int org_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditOrganizations");

        Organization org = objectManager.organizationRepo.getOneOrgWId(org_id);

        mav.addObject("org", org);

        return mav;

    }


//    ******************************************* GET ADMIN PROJECTS ****************************************


// =============== ADMIN ALL PROJECTS ===============

    @GetMapping("/admin/project/{org_id}")
    public String adminLookUpProjects(@PathVariable(name = "org_id")int org_id, Model model){

        List<Project> projectList = objectManager.projectRepo.getAllProjectsWhere(org_id);

        model.addAttribute("projectList", projectList);

        return "admin/adminLookUpProject";
    }

    // =============== EDIT ONE ADMIN PROJECT ===============

    @GetMapping("/admin/project/edit/{project_id}")
    public ModelAndView adminEditProject(@PathVariable(name = "project_id") int project_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditProject");

        Project project                             = objectManager.projectRepo.getOneProject(project_id);
        List<GetProjectJobTitles> projectTitlesList = objectManager.jobTitleRepo.getOneProjectJobTitle(project_id);

        mav.addObject("jobTitlesList", projectTitlesList);
        mav.addObject("project", project);

        return mav;
    }

// =============== INSERT JOBTITLE =================
    @GetMapping("/admin/project/newJobtitles/{project_id}")
    private ModelAndView newjobtitle(@PathVariable(name = "project_id")String project_id, Principal principal, HttpServletRequest request){
        ModelAndView mav                = new ModelAndView("admin/adminCreateJobTitles");
        Map<String, ?> inputFlashMap    = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("error", inputFlashMap.get("error"));
        }

        int idParsed = Integer.parseInt(project_id);

        Project project = objectManager.projectRepo.getOneProject(idParsed);

        mav.addObject("project", project);

        return mav;
    }


//    ******************************************* GET ADMIN NEWS ****************************************

// =============== ALL NEWS =================

    @GetMapping("/admin/news/{fk_orgName}")
    public ModelAndView adminNews(@PathVariable(name = "fk_orgName") String fk_orgName){
        ModelAndView mav = new ModelAndView("admin/adminLookUpNews");

        List<Newsfeed>listNewsfeed  = objectManager.newsRepo.getAllNews(fk_orgName);

        mav.addObject("listNewsfeed", listNewsfeed);

        return mav;
    }

// =============== EDIT NEWS =================

    @GetMapping("/admin/news/edit/{newsfeed_id}")
    private ModelAndView updateNews(@PathVariable(name = "newsfeed_id") int newsfeed_id){
        ModelAndView mav = new ModelAndView("admin/adminEditNews");

        Newsfeed newsfeed = objectManager.newsRepo.getOneNews(newsfeed_id);

        mav.addObject("newsfeed", newsfeed);

        return mav;
    }

//   =================================================================== POST CONTROLLER ==========================================================================

//    ******************************************* ADMIN SUPPORT ****************************************

// ============== SAVE MESSAGE ==============

    @PostMapping("/admin/save/chat")
    public String saveMessage(WebRequest dataFromForm) {
        String user_mail           = (dataFromForm.getRemoteUser());
        String message_context      = (dataFromForm.getParameter("message_context"));
        String ticket_ownerMail    = (dataFromForm.getParameter("ticket_ownerMail"));
        String ticket_id           = (dataFromForm.getParameter("ticket_id"));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String time = projectServices.returnTime(timestamp);

        int ticketIdParsed  = Integer.parseInt(ticket_id);

        User user = objectManager.userRepo.getOneUser(user_mail);

        objectManager.ticketRepo.insertMessage(message_context, time, ticketIdParsed, ticket_ownerMail);
        objectManager.ticketRepo.messageUpdateTicketAdmin(ticketIdParsed);

        return "redirect:/admin/support/assigned/"+user.getUser_id();
    }

// ============== CLOSE TICKET ==============

@PostMapping("/admin/support/closeticket")
public String closeTicket(WebRequest dataFromForm) {
    String user_mail            = (dataFromForm.getRemoteUser());
    String ticket_id            = (dataFromForm.getParameter("ticket_id"));

    int ticketIdParsed  = Integer.parseInt(ticket_id);

    User user = objectManager.userRepo.getOneUser(user_mail);

    objectManager.ticketRepo.closeTicket(ticketIdParsed);

    return "redirect:/admin/support/assigned/"+user.getUser_id();
}

// ============== ASIGN TICKET ==============

    @PostMapping("/admin/support/asignticket")
    public String postAsignedTickets(WebRequest dataFromForm) {
        String user_mail            = (dataFromForm.getRemoteUser());
        String ticket_context       = (dataFromForm.getParameter("ticket_context"));
        String ticket_timestamp     = (dataFromForm.getParameter("ticket_timestamp"));
        String ticket_ownerMail     = (dataFromForm.getParameter("ticket_ownerMail"));
        String ticket_id            = (dataFromForm.getParameter("ticket_id"));

        int ticketIdParsed  = Integer.parseInt(ticket_id);

        User user = objectManager.userRepo.getOneUser(user_mail);

//        ASIGN
        objectManager.ticketRepo.assignTicket(ticketIdParsed, user.getUser_id());
        objectManager.ticketRepo.assignTicket02(ticketIdParsed);

        SupportMessage supportMessage = objectManager.ticketRepo.getOneMessage(ticketIdParsed);

//CHECK IF NOT ASIGNED ALREADY AND ADD TICKET TO DB MESSAGES
        if(supportMessage == null){
            objectManager.ticketRepo.insertMessage(ticket_context, ticket_timestamp, ticketIdParsed, ticket_ownerMail);
        }
        return "redirect:/admin/support/assigned/"+user.getUser_id();
    }


//    ******************************************* POST ADMIN USER ****************************************

    //  ==============  INSERT USER ==============

    @PostMapping("/admin/insert/user")
    public String InsertAdminUser(WebRequest dataFromForm) {
        String firstname = (dataFromForm.getParameter("profile_firstname"));
        String lastname  = (dataFromForm.getParameter("profile_lastname"));
        String address   = (dataFromForm.getParameter("profile_address"));
        String phone     = (dataFromForm.getParameter("profile_phone"));
        String country   = (dataFromForm.getParameter("profile_country"));
        String zipcode   = (dataFromForm.getParameter("profile_zip"));
        String jobTitle  = (dataFromForm.getParameter("profile_jobTitle"));
        String password  = (dataFromForm.getParameter("password"));
        String mail      = (dataFromForm.getParameter("user_mail"));
        String role      = (dataFromForm.getParameter("role"));

        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        objectManager.userRepo.insertUser(mail, password, 1);
        User userObj = objectManager.userRepo.getOneUser(mail);

        objectManager.profileRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());

        objectManager.userRepo.insertAuthUser(role, userObj.getUser_mail());


        return "redirect:/admin/lookupuser";
    }

//   ============= UPDATE CREDENTIALS ========

    @PostMapping("/admin/update/credentials")
    public String updatePassword(WebRequest dataFromForm) {
        String user_id          = (dataFromForm.getParameter("user_id"));
        String user_mail        = (dataFromForm.getParameter("user_mail"));
        String user_password    = (dataFromForm.getParameter("user_password"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password1 = encoder.encode(user_password);

        System.out.println(user_id);

        int idParsed = Integer.parseInt(user_id);

        objectManager.userRepo.updateCredentials(idParsed,user_mail,password1);


        return "redirect:/admin/lookupuser";
    }

    //================== DELETE USER ===================

    @PostMapping("/admin/delete/user")
    public String deleteUser(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));

        int idParsed = Integer.parseInt(user_id);

        objectManager.userRepo.deleteUser(idParsed);

        return "redirect:/admin/lookupuser";
    }

    //================== EDIT PROFILE ===================

    @PostMapping("/admin/update/user")
    public String updateAdminProfile(WebRequest dataFromForm) {
        String profile_id           = (dataFromForm.getParameter("profile_id"));
        String profile_firstname    = (dataFromForm.getParameter("profile_firstname"));
        String profile_lastname     = (dataFromForm.getParameter("profile_lastname"));
        String profile_address      = (dataFromForm.getParameter("profile_address"));
        String profile_phone        = (dataFromForm.getParameter("profile_phone"));
        String profile_country      = (dataFromForm.getParameter("profile_country"));
        String profile_zip          = (dataFromForm.getParameter("profile_zip"));
        String profile_jobTitle     = (dataFromForm.getParameter("profile_jobTitle"));
        String user_mail            = (dataFromForm.getParameter("user_mail"));
        String first_mail           = (dataFromForm.getParameter("first_mail"));
        String role                 = (dataFromForm.getParameter("role"));

        int idParse     = Integer.parseInt(profile_id);
        int phoneParse  = Integer.parseInt(profile_phone);
        int zipParsed   = Integer.parseInt(profile_zip);

        User user = objectManager.userRepo.getOneUser(first_mail);

// UPDATING ...
        objectManager.userRepo.updateMailWUserId(user_mail, user.getUser_id());
        objectManager.profileRepo.updateAdminProfile(idParse,profile_firstname,profile_lastname,profile_address,phoneParse, profile_country, zipParsed, profile_jobTitle, user.getUser_id());
        objectManager.userRepo.updateAuthorities(role, first_mail);

        return "redirect:/admin/lookupuser";
    }


// ******************************************* POST ADMIN PROJECT *******************************************

//  ==================  DELETE PROJECT =============

    @PostMapping("/admin/delete/project")
    public String adminDeleteProject(WebRequest dataFromForm) {
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(project_id);

        objectManager.projectRepo.deleteProject(idParsed);

        return "redirect:/admin";
    }

//==================  UPDATE PROJECT =============

    @PostMapping("/admin/update/project")
    public String updateAdminProject(WebRequest dataFromForm,  Principal principal) throws ParseException {
        String project_id         = (dataFromForm.getParameter("project_id"));
        String project_name       = (dataFromForm.getParameter("project_name"));
        String project_desc       = (dataFromForm.getParameter("project_desc"));
        String project_start      = (dataFromForm.getParameter("project_start"));
        String project_end        = (dataFromForm.getParameter("project_end"));
        String user_mail          = (dataFromForm.getParameter("user_mail"));

        int totalDays   = projectServices.calcTotalDays2(project_start, project_end);
        int idParsed    = Integer.parseInt(project_id);

        User user = objectManager.userRepo.getOneUser(user_mail);

        objectManager.projectRepo.updateProject(idParsed, project_name, project_desc, totalDays, project_start, project_end, user.getFk_orgId());

        return "redirect:/admin/project/edit/"+project_id;
    }

//  =========== ADMIN INSERT JOBTITLE =============

    @PostMapping("/admin/insert/newjobtitle")
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
            objectManager.jobTitleRepo.insertOneProjectJobTitle(jobTitle.getJobTitle_Id(), idParsed);

        // IF JOB TITLE EXIST IN DB - INSERT JOB TITLE TO PROJECT TABLE
        }else{
            if(jobTitleExists == true){

                // IF JOB TITLE EXISTS IN DB AND PROJECT TABLE - RETURN "Job Title Already Exists"
                redirectAttributes.addFlashAttribute("error", "Job Title Already Exists");
                return new RedirectView("/admin/project/newJobtitles/"+project_id);
            }
            objectManager.jobTitleRepo.insertOneProjectJobTitle(jobTitleCheck.getJobTitle_Id(), idParsed);
        }
        return new RedirectView ("/admin/project/edit/"+project_id);
    }

// ================== DELETE PROJECT JOB TITLE =============

    @PostMapping("/admin/delete/projectJobTitle")
    public String deleteJobTitle(WebRequest dataFromForm) {
        String projectJobTitle_id       = (dataFromForm.getParameter("projectJobTitle_id"));
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(projectJobTitle_id);

        objectManager.jobTitleRepo.deleteProjectJobTitle(idParsed);

        return "redirect:/admin/project/edit/"+project_id;
    }

// ******************************************* POST ADMIN ORGANIZATION *******************************************


    //================== UPDATE ORGANIZATION ================

    @PostMapping("/admin/update/org")
    public String updateAdminOrg(WebRequest dataFromForm) {
        String org_id       = (dataFromForm.getParameter("org_id"));
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed   = Integer.parseInt(org_cvr);
        int idParsed    = Integer.parseInt(org_id);

        objectManager.organizationRepo.updateAdminOrg(idParsed, org_name, org_address, cvrParsed);


        return "redirect:/admin/organization";
    }

    //================== DELETE ORGANIZATION ================

    @PostMapping("/admin/delete/org")
    public String deleteAdminOrg(WebRequest dataFromForm) {
        String org_id      = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        objectManager.organizationRepo.deleteAdminOrg(idParsed);


        return "redirect:/admin/organization";
    }

// ******************************************* POST ADMIN NEW *******************************************

    // ============= UPDATE NEWS =============

    @PostMapping("/admin/update/news")
    public String updateAdminNews(WebRequest dataFromForm) {
        String newsfeed_id          = (dataFromForm.getParameter("newsfeed_id"));
        String newsfeed_news        = (dataFromForm.getParameter("newsfeed_news"));
        String newsfeed_title       = (dataFromForm.getParameter("newsfeed_title"));
        String newsfeed_img         = (dataFromForm.getParameter("newsfeed_img"));
        String newsfeed_datetime    = (dataFromForm.getParameter("newsfeed_datetime"));

        int idParse         = Integer.parseInt(newsfeed_id);

        objectManager.newsRepo.updateAdminNews(idParse,newsfeed_news, newsfeed_title, newsfeed_img, newsfeed_datetime);

        return "redirect:/admin/organization";
    }

    @PostMapping("/admin/delete/news")
    public String deleteAdminNews(WebRequest dataFromForm) {
        String newsfeed_id      = (dataFromForm.getParameter("newsfeed_id"));

        int idParsed = Integer.parseInt(newsfeed_id);

        objectManager.newsRepo.deleteAdminNews(idParsed);


        return "redirect:/admin/organization";
    }

}
