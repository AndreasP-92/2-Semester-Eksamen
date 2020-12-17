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

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Controller

public class AdminController {

    JobTitleRepo jobTitleRepo = new JobTitleRepo();

    OrganizationRepo orgRep = new OrganizationRepo();

    ProjectRepo projectRepo = new ProjectRepo();

    ProfileRepo profileRepo = new ProfileRepo();

    UserRepo userRepo = new UserRepo();

    NewsfeedRepo newsRepo = new NewsfeedRepo();

//    ===================

    ProjectServices projectServices = new ProjectServices();

    ObjectManager objectManager = new ObjectManager();

//   ============================================================== GET ROUTES ==================================================================

// =============== Admin Index ===============

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {

//        User user = userRepo.getOneUser(principal.getName());
//        Profile profile = profileRepo.getOneProfile(user.getUser_id());
//
//        model.addAttribute("profile", profile);

        return "admin/adminIndex";
    }


// =============== Admin Look Up User ===============

    @GetMapping("/admin/lookupuser")
    public String adminLookUpUsers(Model model) {

        List<User> userlist = userRepo.getAllUsers();
        model.addAttribute("userlist", userlist);

        return "/admin/adminLookUpUser";
    }

// =============== Admin EDIT PROFILE =================

    @GetMapping("/admin/editprofile/{user_id}")
    public ModelAndView adminEditProfile(@PathVariable(name = "user_id") int user_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditProfile");

        User user = userRepo.getOneUserWId(user_id);

        Profile profile = profileRepo.getOneProfile(user_id);

        System.out.println(profile);

        mav.addObject("profile", profile);

        mav.addObject("user", user);

        return mav;
    }

// =============== ADMIN SUPPORT ALL TICKETS =============

    @GetMapping("/admin/support/all")
    public String adminSupport(Model model, Principal principal) {

        List<SupportTicket> supportTicketList = objectManager.ticketRepo.getAllTickets();
        User user = objectManager.userRepo.getOneUser(principal.getName());

        System.out.println(supportTicketList);

        model.addAttribute("user", user);
        model.addAttribute("supportTicketList", supportTicketList);



        return "admin/adminAllTickets";
    }

// =============== ADMIN SUPPORT ASSIGNED TICKETS =============

    @GetMapping("/admin/support/assigned/{user_id}")
    public ModelAndView assignedTickets(@PathVariable(name = "user_id")int user_id, Principal principal) {
        ModelAndView mav = new ModelAndView("admin/adminAssignedTickets");

        List<GetTicketUser> supportTicketList = objectManager.ticketRepo.getAllUserTickets(user_id);
        User user = objectManager.userRepo.getOneUser(principal.getName());

        System.out.println(supportTicketList);

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

// ===============Admin CREATE USER ===============

    @GetMapping("/admin/createuser")
    public String adminCreateUser() {
        return "admin/adminCreateUser";
    }


// =============== Admin Look Up Organization ===============

    @GetMapping("/admin/lookuporganization")
    public String adminLookUpOrganizations(Model model) {

        List<Organization> orglist = orgRep.getAllOrgs();
        model.addAttribute("orglist", orglist);

        return "/admin/adminLookUpOrganization";
    }


// =============== Admin Edit Organisation ===============

    @GetMapping("/admin/editorganizations/{org_id}")
    public ModelAndView adminEditOrganization(@PathVariable(name = "org_id") int org_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditOrganizations");

        Organization org = orgRep.getOneOrgWId(org_id);

        mav.addObject("org", org);

        return mav;

    }

// =============== Admin Projects ===============

    @GetMapping("/admin/lookupproject")
    public String adminLookUpProjects(Model model){

        List<Project> projectList = projectRepo.getAllProjects();
        model.addAttribute("projectList", projectList);

        return "admin/adminLookUpProject";
    }

// =============== Admin Edit Projects ===============

    @GetMapping("/admin/editprojects/{project_id}")
    public ModelAndView adminEditProject(@PathVariable(name = "project_id") int project_id) {
        ModelAndView mav = new ModelAndView("admin/adminEditProjects");
        Project project     = projectRepo.getOneProject(project_id);
        List<GetProjectJobTitles> projectTitlesList = jobTitleRepo.getOneProjectJobTitle(project_id);

        mav.addObject("jobTitlesList", projectTitlesList);
        mav.addObject("project", project);

        return mav;
    }

//    //Admin News
//    @GetMapping("/admin/lookupnews")
//    public String adminLookUpNews(Model model){
//
//        List<Newsfeed> newsfeedList = newsRepo.getAllNews();
//        model.addAttribute("newsfeedList", newsfeedList);
//
//        return "admin/adminLookUpNews";
//    }

    //Admin News
    @GetMapping("/admin/news/{fk_orgName}")
    public ModelAndView adminNews(@PathVariable(name = "fk_orgName") String fk_orgName){
        ModelAndView mav = new ModelAndView("admin/adminLookUpNews");

        List<Newsfeed>listNewsfeed = newsRepo.getAllNews(fk_orgName);

        Organization organization = orgRep.getOneOrg(fk_orgName);
        Project project = projectRepo.getOneProjectWOrgId(organization.getOrg_id());
        User user = userRepo.getOneUserWOrgId(organization.getOrg_id());
        Profile profile = profileRepo.getOneProfile(user.getUser_id());

        System.out.println(organization);
        System.out.println(project);

        System.out.println(listNewsfeed);
        mav.addObject("listNewsfeed", listNewsfeed);
        mav.addObject("OneOrg", fk_orgName);
        mav.addObject("project", project);
        mav.addObject("profile", profile);

        return mav;
    }

    @GetMapping("/admin/news/edit/{newsfeed_id}")
    private ModelAndView updateNews(@PathVariable(name = "newsfeed_id") int newsfeed_id){
        ModelAndView mav = new ModelAndView("admin/adminEditNews");

        Newsfeed newsfeed = newsRepo.getOneNews(newsfeed_id);
        mav.addObject("newsfeed", newsfeed);

        System.out.println(newsfeed.getNewsfeed_datetime());

        return mav;
    }

//========================================= POST TICKETS =========================================================================

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
// OBJECTS
        User user = objectManager.userRepo.getOneUser(user_mail);

// INSERT MESSAGE
        objectManager.ticketRepo.insertMessage(message_context, time, ticketIdParsed, ticket_ownerMail);
// UPDATE TICKET
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

//CHECK IF NOT ASIGNED ALREADY
        if(supportMessage == null){
            objectManager.ticketRepo.insertMessage(ticket_context, ticket_timestamp, ticketIdParsed, ticket_ownerMail);
        }
        return "redirect:/admin/support/assigned/"+user.getUser_id();
    }

//========================================= POST ROUTES =========================================================================

    //    INSERT USER ==============

    @PostMapping("/admin/insert/user")
    public String InsertAdminUser(WebRequest dataFromForm, Principal principal) {
        String firstname = (dataFromForm.getParameter("firstname"));
        String lastname  = (dataFromForm.getParameter("lastname"));
        String address   = (dataFromForm.getParameter("address"));
        String phone     = (dataFromForm.getParameter("phone"));
        String country   = (dataFromForm.getParameter("country"));
        String zipcode   = (dataFromForm.getParameter("zipcode"));
        String jobTitle  = (dataFromForm.getParameter("jobTitle"));
        String password  = (dataFromForm.getParameter("password"));
        String mail      = (dataFromForm.getParameter("mail"));


        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        userRepo.insertUser(mail, password, 1);
        User userObj = userRepo.getOneUser(mail);

        System.out.println(userObj);

        profileRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());

        userRepo.insertAuthUser("ROLE_USER", userObj.getUser_mail());


        return "redirect:/";
    }



////========================================= Admin EDIT PROFILE =======================================================================

//================== Admin EDIT PROFILE ===================
    @PostMapping("/admin/update/user")
    public String updateAdminProfile(WebRequest dataFromForm,  Principal principal) {
        String profile_id           = (dataFromForm.getParameter("profile_id"));
        String profile_firstname    = (dataFromForm.getParameter("profile_firstname"));
        String profile_lastname     = (dataFromForm.getParameter("profile_lastname"));
        String profile_address      = (dataFromForm.getParameter("profile_address"));
        String profile_phone        = (dataFromForm.getParameter("profile_phone"));
        String profile_country      = (dataFromForm.getParameter("profile_country"));
        String profile_zip          = (dataFromForm.getParameter("profile_zip"));
        String profile_jobTitle     = (dataFromForm.getParameter("profile_jobTitle"));
        String password             = (dataFromForm.getParameter("user_password"));
        String mail                 = (dataFromForm.getParameter("user_mail"));
        String role                 = (dataFromForm.getParameter("role"));

        int idParse     = Integer.parseInt(profile_id);
        int phoneParse  = Integer.parseInt(profile_phone);
        int zipParsed   = Integer.parseInt(profile_zip);

        userRepo.insertUser(mail, password, 1);
        User userObj = userRepo.getOneUser(mail);

        System.out.println(userObj);

        User user = userRepo.getOneUser(principal.getName());

        profileRepo.updateAdminProfile(idParse,profile_firstname,profile_lastname,profile_address,phoneParse, profile_country, zipParsed, profile_jobTitle, user.getUser_id());

        userRepo.insertAuthUser(role, userObj.getUser_mail());

        return "redirect:/";
    }

    @PostMapping("/admin/delete/user")
    public String deleteUser(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));

        int idParsed = Integer.parseInt(user_id);

        profileRepo.deleteUser(idParsed);

        return "redirect:/admin/lookupuser";
    }



//========================================================ADMIN PROJECT===============================================================

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

        User user = userRepo.getOneUser(user_mail);

        projectRepo.updateProject(idParsed, project_name, project_desc, totalDays, project_start, project_end, user.getFk_orgId());

        return "redirect:/";
    }


    //ADMIN DELETE PROJECT

    @PostMapping("/admin/delete/project")
    public String deleteAdminProject(WebRequest dataFromForm) {
        String project_id               = (dataFromForm.getParameter("project_id"));

        int idParsed = Integer.parseInt(project_id);

        projectRepo.deleteAdminProject(idParsed);


        return "redirect:/admin/lookupproject";
    }

//========================================================ADMIN Organisation===============================================================

    //    ================== ADMIN INSERT ORGANIZATION ================
//    @PostMapping("/admin/edit/organization")
//    public String postAdminOrganization(WebRequest dataFromForm, Principal principal) {
//        String org_name         = (dataFromForm.getParameter("org_name"));
//        String org_address      = (dataFromForm.getParameter("org_address"));
//        String org_cvr          = (dataFromForm.getParameter("org_cvr"));
//
//        int cvrParsed = Integer.parseInt(org_cvr);
//
//        orgRep.insertOrg(org_name, org_address, cvrParsed);
//
//        User user           = userRepo.getOneUser(principal.getName());
//        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());
//
//        orgRep.insertOrg(org_name, org_address, cvrParsed);
//
//        return "redirect:/";
//    }

    //================== ADMIN UPDATE ORGANIZATION ================

    @PostMapping("/admin/update/org")
    public String updateAdminOrg(WebRequest dataFromForm) {
        String org_id       = (dataFromForm.getParameter("org_id"));
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed   = Integer.parseInt(org_cvr);
        int idParsed    = Integer.parseInt(org_id);

        orgRep.updateAdminOrg(idParsed, org_name, org_address, cvrParsed);


        return "redirect:/";
    }

    @PostMapping("/admin/delete/org")
    public String deleteAdminOrg(WebRequest dataFromForm) {
        String org_id      = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        orgRep.deleteAdminOrg(idParsed);


        return "redirect:/admin/lookuporganization";
    }

//========================================================ADMIN NEWS===============================================================

    //    UPDATE NEWS =============

    @PostMapping("/admin/update/news")
    public String updateAdminNews(WebRequest dataFromForm) {
        String newsfeed_id          = (dataFromForm.getParameter("newsfeed_id"));
        String newsfeed_news        = (dataFromForm.getParameter("newsfeed_news"));
        String newsfeed_title       = (dataFromForm.getParameter("newsfeed_title"));
        String newsfeed_img         = (dataFromForm.getParameter("newsfeed_img"));
        String newsfeed_datetime    = (dataFromForm.getParameter("newsfeed_datetime"));

        int idParse         = Integer.parseInt(newsfeed_id);

        newsRepo.updateAdminNews(idParse,newsfeed_news, newsfeed_title, newsfeed_img, newsfeed_datetime);

        return "redirect:/admin/lookuporganization";
    }

    @PostMapping("/admin/delete/news")
    public String deleteAdminNews(WebRequest dataFromForm) {
        String newsfeed_id      = (dataFromForm.getParameter("newsfeed_id"));

        int idParsed = Integer.parseInt(newsfeed_id);

        newsRepo.deleteAdminNews(idParsed);


        return "redirect:/admin/lookuporganization";
    }

}
