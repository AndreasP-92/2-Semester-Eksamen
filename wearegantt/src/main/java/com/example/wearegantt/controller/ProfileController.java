package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.repository.*;
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

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ProfileController {

//REPOSITORIES ====================

    OrganizationRepo orgRep = new OrganizationRepo();

    UserRepo userRepo       = new UserRepo();

    ProfileRepo profileRepo = new ProfileRepo();

    ProjectRepo projectRepo = new ProjectRepo();


//    =================

    ProjectServices projectServices = new ProjectServices();
    ObjectManager objectManager = new ObjectManager();



//   =========================================== GET ROUTES ===================================




// EDIT PROFILE =================
    @GetMapping("/editprofile/{user_mail}")
    private ModelAndView profile(@PathVariable(name = "user_mail")String user_mail){
        ModelAndView mav = new ModelAndView("profile/editProfile");

        User user           = userRepo.getOneUser(user_mail);
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());
        Profile profile     = profileRepo.getOneProfile(user.getUser_id());

        mav.addObject("profile", profile);
        mav.addObject("org", org);
        mav.addObject("user", user);
        mav.addObject("activePage", "profile");


        System.out.println(user);

        return mav;
    }

// NEW ORGANIZATION =================

    @GetMapping("/profile/organization")
    private String newOrganization(Model model, Principal principal){

        User user                   = userRepo.getOneUser(principal.getName());
        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());

        model.addAttribute("org", organization);
        model.addAttribute("user", user);

        return "profile/newOrganization";
    }

// UPDATE ORGANIZATION =================


    @GetMapping("/profile/organization/{org_name}")
    public ModelAndView Organization(@PathVariable(name = "org_name")String org_name, Principal principal){
        ModelAndView mav    = new ModelAndView("profile/editOrganization");

        Organization org    = orgRep.getOneOrg(org_name);
        User user           = userRepo.getOneUser(principal.getName());

        mav.addObject("user", user);
        mav.addObject("org", org);

        return mav;
    }

// =============== PROFILE SUPPORT ===============

    @GetMapping("/profile/support")
    public String profileSupport(Model model, Principal principal) {

        User user = objectManager.userRepo.getOneUser(principal.getName());
        List<SupportTicket> supportTicketList = objectManager.ticketRepo.getAllTicketsWUserMail(user.getUser_mail());
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());
        Profile profile = objectManager.profileRepo.getOneProfile(user.getUser_id());


        System.out.println(supportTicketList);

        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        model.addAttribute("org", org);
        model.addAttribute("user", user);
        model.addAttribute("supportTicketList", supportTicketList);



        return "profile/profileTicket";
    }

// =============== PROFILE TICKET =================

    @GetMapping("/profile/ticket/{profile_id}")
    private ModelAndView ticket(@PathVariable(name = "profile_id") int profile_id, Principal principal){
        ModelAndView mav    = new ModelAndView("profile/support");

        User user           = userRepo.getOneUser(principal.getName());
        Profile profile     = profileRepo.getOneProfile(user.getUser_id());
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());


//        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());

        mav.addObject("org", org);
        mav.addObject("user", user);
        mav.addObject("profile", profile);

        return mav;
    }

// =============== PROFILE CHAT ===============

@GetMapping("/profile/support/chat/{ticket_id}")
public ModelAndView profileChat(@PathVariable(name = "ticket_id")int ticket_id, Principal principal) {
    ModelAndView mav = new ModelAndView("profile/profileChat");

    List<SupportMessage> supportMessageList = objectManager.ticketRepo.getAllMessagesWTicketId(ticket_id);
    SupportTicket supportTicket             = objectManager.ticketRepo.getOneTicket(ticket_id);
    User user                               = objectManager.userRepo.getOneUser(principal.getName());
    Profile profile                         = objectManager.profileRepo.getOneProfile(user.getUser_id());

    mav.addObject("supportMessageList", supportMessageList);
    mav.addObject("supportTicket", supportTicket);
    mav.addObject("profile", profile);


    return mav;
}

//  =================================  POST ROUTES =============================

    // CREATE TICKET =======================

//    @PostMapping("/insert/ticket")
//    public String postUserTicket(WebRequest dataFromForm, Principal principal) {
//        String ticket_title         = (dataFromForm.getParameter("ticket_title"));
//        String ticket_ownerName     = (dataFromForm.getParameter("ticket_ownerName"));
//        String ticket_ownerMail     = (dataFromForm.getParameter("ticket_ownerMail"));
//        String ticket_context       = (dataFromForm.getParameter("ticket_context"));
//        String user                 = dataFromForm.getRemoteUser();
//        int ticket_active           = 1;
//        int ticket_taken            = 0;
//
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//
//        System.out.println("MAIL============="+ticket_ownerName);
//        System.out.println("USER============="+user);
//
//        objectManager.ticketRepo.insertSupportTicket(ticket_title, ticket_context, timestamp, ticket_ownerMail, ticket_ownerName, ticket_active, ticket_taken);
//        if(user == null){
//            return "redirect:/";
//        } else{
//
//            return "redirect:/editprofile/"+principal.getName();
//        }
//
//
//    }

    // ============== SAVE MESSAGE ==============

    @PostMapping("/profile/save/chat")
    public String saveMessage(WebRequest dataFromForm) {
        String user_mail           = (dataFromForm.getRemoteUser());
        String message_context      = (dataFromForm.getParameter("message_context"));
        String ticket_ownerMail    = (dataFromForm.getParameter("ticket_ownerMail"));
        String ticket_id           = (dataFromForm.getParameter("ticket_id"));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String time = projectServices.returnTime(timestamp);

        int ticketIdParsed  = Integer.parseInt(ticket_id);


// INSERT MESSAGE
        objectManager.ticketRepo.insertMessage(message_context, time, ticketIdParsed, ticket_ownerMail);
// UPDATE MESSAGE
        objectManager.ticketRepo.messageUpdateTicketUser(ticketIdParsed);

        return "redirect:/profile/support";
    }

//    INSERT ORGANIZATION =============

    @PostMapping("/insert/org")
    public String postOrg(WebRequest dataFromForm,  Principal principal) {
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);

        orgRep.insertOrg(org_name, org_address, cvrParsed);

        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrg(org_name);

        userRepo.updateUserWId(user.getUser_id(), org.getOrg_id());

        return "redirect:/";
    }

//    DELETE ORGANIZATION =============

    @PostMapping("/delete/org")
    public String deleteOrg(WebRequest dataFromForm) {
        String org_id      = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        orgRep.deleteOrg(idParsed);


        return "redirect:/";
    }

    //    INVITE USER ORGANIZATION =============

    @PostMapping("/insert/org/user")
    public String inviteUserToOrg(WebRequest dataFromForm) {
        String user_mail  = (dataFromForm.getParameter("user_mail"));
        String org_id     = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        User user = userRepo.getOneUser(user_mail);

        userRepo.updateUserWId(user.getUser_id(), idParsed);

        return "redirect:/";
    }

//    UPDATE ORGANIZATION =============


    @PostMapping("/update/org")
    public String updateOrg(WebRequest dataFromForm) {
        String org_id       = (dataFromForm.getParameter("org_id"));
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed   = Integer.parseInt(org_cvr);
        int idParsed    = Integer.parseInt(org_id);

        orgRep.updateOrg(idParsed, org_name, org_address, cvrParsed);


        return "redirect:/";
    }

//    UPDATE PROFILE =============

    @PostMapping("/update/profile")
    public String updateProfile(WebRequest dataFromForm,  Principal principal) {
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

    //    DELETE PROFILE ========

    @PostMapping("/delete/profile")
    public String deleteProfile(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));

        int idParsed = Integer.parseInt(user_id);

        userRepo.disableUser(idParsed);

        return "redirect:/login?logout";
    }


    //    UPDATE CREDENTIALS ========

    @PostMapping("/update/credentials")
    public String updatePassword(WebRequest dataFromForm) {
        String user_id          = (dataFromForm.getParameter("user_id"));
        String user_mail        = (dataFromForm.getParameter("user_mail"));
        String user_password    = (dataFromForm.getParameter("user_password"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password1 = encoder.encode(user_password);

        int idParsed = Integer.parseInt(user_id);
        if(user_password==""){
            userRepo.updateEmail(idParsed,user_mail);
        }else {
            userRepo.updateCredentials(idParsed,user_mail,password1);
        }

        return "redirect:/login?logout";
    }
//    INSERT TICKET =============

}
