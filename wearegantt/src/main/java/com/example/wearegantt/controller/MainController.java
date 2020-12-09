package com.example.wearegantt.controller;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.SupportTicket;
import com.example.wearegantt.model.TicketUser;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.SupportTicketRepo;
import com.example.wearegantt.repository.OrganizationRepo;
import com.example.wearegantt.repository.TicketRepo;
import com.example.wearegantt.repository.NewsfeedRepo;
import com.example.wearegantt.repository.OrganizationRepo;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@org.springframework.stereotype.Controller
public class MainController {

    SupportTicketRepo supportTicketRepo = new SupportTicketRepo();

    OrganizationRepo orgRep = new OrganizationRepo();

    UserRepo userRepo = new UserRepo();

    TicketRepo ticketRepo = new TicketRepo();


    NewsfeedRepo newsRepo = new NewsfeedRepo();

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

    @GetMapping("/newsfeed")
    private String newsfeed(Model model){
        List<Newsfeed> listNewsfeed = newsRepo.getAllNews();
        model.addAttribute("listNewsfeed", listNewsfeed);

        return "main/newsfeed";
    }

    @GetMapping("/createNewsfeed")
    private String createNewsfeed(){

        return "main/createNewsfeed";
        //        private ModelAndView createNewsfeed(Model model){
//            ModelAndView mav = new ModelAndView("newsfeed/createNewsfeed");
//
//            return mav;
    }

    @GetMapping("/editNewsfeed")
    private String editNewsfeed(){

        return "main/editNewsfeed";

    }
//    POST ROUTES ==================
@GetMapping("/newsfeed/create")
private ModelAndView createnewsfeed(Principal principal) {
    ModelAndView mav = new ModelAndView("main/createNewsfeed");
//        User user = userRepo.getOneUser(principal.getName());
//        List<JobTitle> jobTitles = projectRepo.getAllJobTitlesWOrg(user.getFk_orgId());
//
//        System.out.println(jobTitles);

}
// INSERT PROJECT =======================

//    @PostMapping("/insert/news")
//    public String postNews(WebRequest dataFromForm, Principal principal) {
//        String newsfeed_news     = (dataFromForm.getParameter("newsfeed_news"));
//        String newsfeed_title     = (dataFromForm.getParameter("newsfeed_title"));
//        String newsfeed_img = (dataFromForm.getParameter("newsfeed_img"));
//        String newsfeed_datetime    = (dataFromForm.getParameter("newsfeed_datetime"));
//
//
//        Newsfeed news   = newsRepo.getOneNews(principal.getName());
//        Organization org  = orgRep.getOneNews(news.getFk_orgName());
//
//        NewsfeedRepo.InsertNews(newsfeed_news, newsfeed_title, newsfeed_img, newsfeed_datetime, org.getOrg_id());
//
//        return "redirect:/";
//    }
//    }
//        User user = userRepo.getOneUser(principal.getName());
//        Organization organization = orgRep.getOneOrgWId(user.getFk_orgId());

//        mav.addObject("jobTitlesList", jobTitles);

//    return mav;
//}

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
