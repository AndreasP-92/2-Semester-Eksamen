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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

//    =================== SERVICES ==================

    ProjectServices projectServices = new ProjectServices();
    ObjectManager objectManager = new ObjectManager();

//   =================================================================== GET CONTROLLER ==========================================================================



//    ******************************************* GET PROFILE *******************************************


// ================= EDIT PROFILE =================
    @GetMapping("/profile/{user_mail}")
    private ModelAndView profile(@PathVariable(name = "user_mail")String user_mail){
        ModelAndView mav = new ModelAndView("profile/editProfile");

        User user                   = objectManager.userRepo.getOneUser(user_mail);
        Organization org            = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Profile profile             = objectManager.profileRepo.getOneProfile(user.getUser_id());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        System.out.println(authorities.getAuth_role());

        System.out.println(user);

        mav.addObject("auth", authorities);
        mav.addObject("profile", profile);
        mav.addObject("org", org);
        mav.addObject("user", user);
        mav.addObject("activePage", "profile");

        return mav;
    }


//    ******************************************* GET ORGANIZATION *******************************************

// ================= NEW ORGANIZATION =================

    @GetMapping("/profile/organization")
    private String newOrganization(Model model, Principal principal){

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        model.addAttribute("auth", authorities);
        model.addAttribute("org", organization);
        model.addAttribute("user", user);

        return "profile/newOrganization";
    }

// =================UPDATE ORGANIZATION =================


    @GetMapping("/profile/organization/{org_name}")
    public ModelAndView Organization(HttpServletRequest request, @PathVariable(name = "org_name")String org_name, Principal principal){
        ModelAndView mav    = new ModelAndView("profile/editOrganization");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("error", inputFlashMap.get("error"));
        }

        Organization org            = objectManager.organizationRepo.getOneOrg(org_name);
        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("user", user);
        mav.addObject("org", org);

        return mav;
    }

//    ******************************************* GET PROFILE SUPPORT *******************************************

// =============== PROFILE SUPPORT ===============

    @GetMapping("/profile/support")
    public String profileSupport(Model model, Principal principal) {

        User user                               = objectManager.userRepo.getOneUser(principal.getName());
        List<SupportTicket> supportTicketList   = objectManager.ticketRepo.getAllTicketsWUserMail(user.getUser_mail());
        Organization org                        = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Profile profile                         = objectManager.profileRepo.getOneProfile(user.getUser_id());
        Authorities authorities                 = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        model.addAttribute("auth", authorities);
        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        model.addAttribute("org", org);
        model.addAttribute("user", user);
        model.addAttribute("supportTicketList", supportTicketList);
        model.addAttribute("activePage", "support");

        return "profile/profileTicket";
    }

// =============== PROFILE TICKET =================

    @GetMapping("/profile/ticket/{profile_id}")
    private ModelAndView ticket(@PathVariable(name = "profile_id") int profile_id, Principal principal){
        ModelAndView mav    = new ModelAndView("profile/support");

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Profile profile             = objectManager.profileRepo.getOneProfile(user.getUser_id());
        Organization org            = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
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

//   =================================================================== GET CONTROLLER ==========================================================================

//    ******************************************* POST PROFILE SUPPORT *******************************************


// ============== SAVE MESSAGE ==============

    @PostMapping("/profile/save/chat")
    public String saveMessage(WebRequest dataFromForm) {
        String message_context     = (dataFromForm.getParameter("message_context"));
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

//    ******************************************* POST ORGANIZATION *******************************************


//   ============= INSERT ORGANIZATION =============

    @PostMapping("/insert/org")
    public String postOrg(WebRequest dataFromForm,  Principal principal) {
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed = Integer.parseInt(org_cvr);

        objectManager.organizationRepo.insertOrg(org_name, org_address, cvrParsed);

        User user           = objectManager.userRepo.getOneUser(principal.getName());
        Organization org    = objectManager.organizationRepo.getOneOrg(org_name);

        objectManager.userRepo.updateUserWId(user.getUser_id(), org.getOrg_id());

        return "redirect:/profile/"+user.getUser_mail();
    }

//   ============= DELETE ORGANIZATION =============

    @PostMapping("/delete/org")
    public String deleteOrg(WebRequest dataFromForm) {
        String org_id      = (dataFromForm.getParameter("org_id"));

        int idParsed = Integer.parseInt(org_id);

        User user = objectManager.userRepo.getOneUserWOrgId(idParsed);

        objectManager.organizationRepo.deleteOrg(idParsed);


        return "redirect:/profile/"+user.getUser_mail();
    }

//   ============= INVITE USER ORGANIZATION =============

    @PostMapping("/insert/org/user")
    public RedirectView inviteUserToOrg(RedirectAttributes redirectAttributes, WebRequest dataFromForm, Principal principal) {
        String user_mail  = (dataFromForm.getParameter("user_mail"));
        String org_id     = (dataFromForm.getParameter("org_id"));

        System.out.println(user_mail);

        int idParsed        = Integer.parseInt(org_id);

        User currentUser            = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(currentUser.getFk_orgId());

        // CHECKS IF USER EXISTS IN DB
        boolean userCheck   = objectManager.userRepo.checkUsernameExists02(user_mail);

        //  IF USER EXISTS RETURN Account Exists Allready
        if(userCheck == true){
            redirectAttributes.addFlashAttribute("error", "Account doesn't exists");
            return new RedirectView("/profile/organization/"+organization.getOrg_name());

        // IF USER DOESN'T EXIST
        }

        redirectAttributes.addFlashAttribute("error", "Success");
        User user = objectManager.userRepo.getOneUser(user_mail);
        objectManager.userRepo.updateUserWId(user.getUser_id(), idParsed);
        return new RedirectView("/profile/organization/"+organization.getOrg_name());
    }

//   ============= UPDATE ORGANIZATION =============


    @PostMapping("/update/org")
    public String updateOrg(WebRequest dataFromForm) {
        String org_id       = (dataFromForm.getParameter("org_id"));
        String org_address  = (dataFromForm.getParameter("org_address"));
        String org_name     = (dataFromForm.getParameter("org_name"));
        String org_cvr      = (dataFromForm.getParameter("org_cvr"));

        int cvrParsed   = Integer.parseInt(org_cvr);
        int idParsed    = Integer.parseInt(org_id);

        User user = objectManager.userRepo.getOneUserWOrgId(idParsed);

        objectManager.organizationRepo.updateOrg(idParsed, org_name, org_address, cvrParsed);


        return "redirect:/profile/"+user.getUser_mail();
    }

//    ******************************************* POST PROFILE *******************************************


//   ============= UPDATE PROFILE =============

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

        User user = objectManager.userRepo.getOneUser(principal.getName());

        objectManager.profileRepo.updateProfile(idParse,profile_firstname,profile_lastname,profile_address,phoneParse, profile_country, zipParsed, profile_jobTitle, user.getUser_id());

        return "redirect:/profile/"+user.getUser_mail();
    }

//   ============= UPDATE PROFILE SUBSCRIPTION ========

    @PostMapping("/update/profile/subscription")
    public String updateSub(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));
        String role         = (dataFromForm.getParameter("role"));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int userIdParsed = Integer.parseInt(user_id);

        User user = objectManager.userRepo.getOneUserWId(userIdParsed);

//      IF NORMALUSER SELECTED -- 4 EUR
        if(role.equals("ROLE_NORMALUSER")){
            objectManager.userRepo.insertPayment(4, timestamp, user.getUser_id());
        }

//      IF SUPERUSER SELECTED -- 12 EUR
        if(role.equals("ROLE_SUPERUSER")){
            objectManager.userRepo.insertPayment(12, timestamp, user.getUser_id());
        }
        objectManager.userRepo.updateAuthorities(role, user.getUser_mail());

        return "redirect:/profile/"+user.getUser_mail();
    }

    //   ============= DELETE PROFILE ========

    @PostMapping("/delete/profile")
    public String deleteProfile(WebRequest dataFromForm) {
        String user_id      = (dataFromForm.getParameter("user_id"));

        int idParsed = Integer.parseInt(user_id);

        objectManager.userRepo.disableUser(idParsed);

        return "redirect:/login?logout";
    }




    //   ============= UPDATE CREDENTIALS ========

    @PostMapping("/update/credentials")
    public String updatePassword(WebRequest dataFromForm) {
        String user_id          = (dataFromForm.getParameter("user_id"));
        String user_mail        = (dataFromForm.getParameter("user_mail"));
        String user_password    = (dataFromForm.getParameter("user_password"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password1 = encoder.encode(user_password);

        System.out.println(user_id);

        int idParsed = Integer.parseInt(user_id);

//      IF PASSWORD EMPTY, ONLY UPDATE EMAIL
        if(user_password==""){
            objectManager.userRepo.updateEmail(idParsed,user_mail);
        }else {
            objectManager.userRepo.updateCredentials(idParsed,user_mail,password1);
        }

        return "redirect:/profile/"+user_mail;
    }

}
