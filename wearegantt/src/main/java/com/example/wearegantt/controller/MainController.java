package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.repository.*;
import com.example.wearegantt.repository.OrganizationRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@org.springframework.stereotype.Controller
public class MainController {

    SupportTicketRepo supportTicketRepo = new SupportTicketRepo();

    OrganizationRepo orgRep = new OrganizationRepo();

    UserRepo userRepo = new UserRepo();

    TicketRepo ticketRepo = new TicketRepo();

    NewsfeedRepo newsRepo = new NewsfeedRepo();

    ProfileRepo profRepo = new ProfileRepo();

    ProjectRepo projRepo = new ProjectRepo();



//    OrganizationRepo orgRep = new OrganizationRepo();


//    =================================== GET ROUTES ==================

    @GetMapping("/")
    private String index(){

        return "main/index";
    }

    // CONTACT ==========

    @GetMapping("/contact")
    private String contact(){
        return "main/contact";
    }

    // FAQ ==========

    @GetMapping("/faq")
    private String faq(){
        return "main/faq";
    }

    // PRIVACY ==========

    @GetMapping("/privacy")
    private String privacy(){
        return "main/privacy";
    }

    // NEWSFEED ==========

    @GetMapping("/newsfeed/{fk_orgName}")
    private ModelAndView newsfeed(@PathVariable(name = "fk_orgName") String fk_orgName){
        ModelAndView mav = new ModelAndView("main/newsfeed");

        List<Newsfeed>listNewsfeed = newsRepo.getAllNews(fk_orgName);

        Organization organization = orgRep.getOneOrg(fk_orgName);
        Project project = projRepo.getOneProjectWOrgId(organization.getOrg_id());
        User user = userRepo.getOneUserWOrgId(organization.getOrg_id());
        Profile profile = profRepo.getOneProfile(user.getUser_id());


        System.out.println(listNewsfeed);
        mav.addObject("listNewsfeed", listNewsfeed);
        mav.addObject("org", organization);
        mav.addObject("project", project);
        mav.addObject("profile", profile);
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/create/newsfeed")
    private ModelAndView createNewsfeed(Principal principal) {
        ModelAndView mav = new ModelAndView("main/createNewsfeed");

        User user                   = userRepo.getOneUser(principal.getName());
        Organization organization   = orgRep.getOneOrgWId(user.getFk_orgId());

        mav.addObject("org", organization);
        mav.addObject("user", user);

        return mav;
    }


    //    UPDATE NEWS ======================

    @GetMapping("/edit/newsfeed/{newsfeed_id}")
    private ModelAndView updateNews(@PathVariable(name = "newsfeed_id") int newsfeed_id){
        ModelAndView mav = new ModelAndView("main/editNewsfeed");

        Newsfeed newsfeed = newsRepo.getOneNews(newsfeed_id);
        mav.addObject("newsfeed", newsfeed);

        System.out.println(newsfeed.getNewsfeed_datetime());

        return mav;
    }


//    @GetMapping("/newsfeed/create")
//    private ModelAndView createnewsfeed(Principal principal) {
//        ModelAndView mav = new ModelAndView("main/createNewsfeed");
//
//        User user           = userRepo.getOneUser(principal.getName());
//        mav.addObject("user", user);
//
//        return mav;
//    }

//    POST ROUTES NEWS   ==================


    //    UPDATE NEWS =============

    @PostMapping("/update/newsfeed")
    public String updateNews(WebRequest dataFromForm, Principal principal) {
        String newsfeed_id          = (dataFromForm.getParameter("newsfeed_id"));
        String newsfeed_news        = (dataFromForm.getParameter("newsfeed_news"));
        String newsfeed_title       = (dataFromForm.getParameter("newsfeed_title"));
        String newsfeed_img         = (dataFromForm.getParameter("newsfeed_img"));
        String newsfeed_datetime    = (dataFromForm.getParameter("newsfeed_datetime"));

        int idParse         = Integer.parseInt(newsfeed_id);

        User user           = userRepo.getOneUser(principal.getName());
        Organization org    = orgRep.getOneOrgWId(user.getFk_orgId());

        newsRepo.updateNews(idParse,newsfeed_news, newsfeed_title, newsfeed_img, newsfeed_datetime, org.getOrg_name());

        return "redirect:/newsfeed/" + org.getOrg_name();
    }



// INSERT NEWS =======================

    @PostMapping("/insert/news")
    public String postNews(WebRequest dataFromForm, Principal principal) {
        String newsfeed_news     = (dataFromForm.getParameter("newsfeed_news"));
        String newsfeed_title    = (dataFromForm.getParameter("newsfeed_title"));
        String newsfeed_img    = (dataFromForm.getParameter("newsfeed_img"));

        User user = userRepo.getOneUser(principal.getName());
        Organization org = orgRep.getOneOrgWId(user.getFk_orgId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSSSSS");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        newsRepo.insertNews(newsfeed_news, newsfeed_title, newsfeed_img, sdf.format(timestamp), org.getOrg_name());

        return "redirect:/newsfeed/" + org.getOrg_name();
    }


    //    DELETE NEWS =============

    @PostMapping("/delete/newsfeed")
    public String deleteNews(WebRequest dataFromForm) {
        String newsfeed_id               = (dataFromForm.getParameter("newsfeed_id"));

        int idParsed = Integer.parseInt(newsfeed_id);

        newsRepo.deleteNews(idParsed);

        System.out.println(idParsed);

        return "redirect:/";
    }

    //  =================================  POST ROUTES For TICKETS =============================

    //    INSERT TICKET =============

    @PostMapping("/insert/ticket")
    public String postTicket(WebRequest dataFromForm) {
        String ticket_title       = (dataFromForm.getParameter("ticket_title"));
        String ticket_context     = (dataFromForm.getParameter("ticket_context"));
        String ticket_ownerMail   = (dataFromForm.getParameter("ticket_ownerMail"));
        String ticket_ownerName   = (dataFromForm.getParameter("ticket_ownerName"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSSSSS");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        supportTicketRepo.InsertTicket(ticket_title, ticket_context, sdf.format(timestamp), ticket_ownerMail, ticket_ownerName);

//        User user           = userRepo.getOneUser(principal.getName());
//
//        SupportTicket supportTicket = supportTicketRepo.getOneTicket(ticket_ownerMail);
//
//        ticketRepo.InsertTicketUser(supportTicket.getTicket_id(), user.getUser_id());
//        //indsæt ticketUser med tidligere ticket_id + user_id

        return "redirect:/";
    }
}
