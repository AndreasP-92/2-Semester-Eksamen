package com.example.wearegantt.controller;

import com.example.wearegantt.model.*;
import com.example.wearegantt.services.ObjectManager;
import org.springframework.stereotype.Controller;
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
public class NewsfeedController {

    //    =================== SERVICES ==================

    ObjectManager objectManager = new ObjectManager();

//   =================================================================== GET CONTROLLER ==========================================================================

// ============ NEWSFEED ============

    @GetMapping("/newsfeed/{fk_orgName}")
    private ModelAndView newsfeed(@PathVariable(name = "fk_orgName") String fk_orgName, Principal principal){
        ModelAndView mav = new ModelAndView("newsfeed/newsfeed");

        List<Newsfeed> listNewsfeed = objectManager.newsRepo.getAllNews(fk_orgName);

        Organization organization   = objectManager.organizationRepo.getOneOrg(fk_orgName);
        Project project             = objectManager.projectRepo.getOneProjectWOrgId(organization.getOrg_id());
        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Profile profile             = objectManager.profileRepo.getOneProfile(user.getUser_id());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("listNewsfeed", listNewsfeed);
        mav.addObject("org", organization);
        mav.addObject("project", project);
        mav.addObject("profile", profile);
        mav.addObject("user", user);
        mav.addObject("activePage", "newsfeed");


        return mav;
    }

// ============ CREATE NEWSFEED ============

    @GetMapping("/create/newsfeed")
    private ModelAndView createNewsfeed(Principal principal) {
        ModelAndView mav = new ModelAndView("newsfeed/createNewsfeed");

        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("auth", authorities);
        mav.addObject("org", organization);
        mav.addObject("user", user);

        return mav;
    }


//  ======================  UPDATE NEWS ======================

    @GetMapping("/edit/newsfeed/{newsfeed_id}")
    private ModelAndView updateNews(@PathVariable(name = "newsfeed_id") int newsfeed_id, Principal principal){
        ModelAndView mav = new ModelAndView("newsfeed/editNewsfeed");

        Newsfeed newsfeed           = objectManager.newsRepo.getOneNews(newsfeed_id);
        User user                   = objectManager.userRepo.getOneUser(principal.getName());
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());
        Authorities authorities     = objectManager.userRepo.getOneAuthWUserMail(user.getUser_mail());

        mav.addObject("newsfeed", newsfeed);
        mav.addObject("auth", authorities);
        mav.addObject("org", organization);
        mav.addObject("user", user);

        return mav;
    }

//   =================================================================== POST CONTROLLER ==========================================================================


//  ============= UPDATE NEWS =============

    @PostMapping("/update/newsfeed")
    public String updateNews(WebRequest dataFromForm, Principal principal) {
        String newsfeed_id          = (dataFromForm.getParameter("newsfeed_id"));
        String newsfeed_news        = (dataFromForm.getParameter("newsfeed_news"));
        String newsfeed_title       = (dataFromForm.getParameter("newsfeed_title"));
        String newsfeed_img         = (dataFromForm.getParameter("newsfeed_img"));
        String newsfeed_datetime    = (dataFromForm.getParameter("newsfeed_datetime"));

        int idParse         = Integer.parseInt(newsfeed_id);

        User user           = objectManager.userRepo.getOneUser(principal.getName());
        Organization org    = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());

        objectManager.newsRepo.updateNews(idParse,newsfeed_news, newsfeed_title, newsfeed_img, newsfeed_datetime, org.getOrg_name());

        return "redirect:/newsfeed/" + org.getOrg_name();
    }



//  ======================= INSERT NEWS =======================

    @PostMapping("/insert/news")
    public String postNews(WebRequest dataFromForm, Principal principal) {
        String newsfeed_news     = (dataFromForm.getParameter("newsfeed_news"));
        String newsfeed_title    = (dataFromForm.getParameter("newsfeed_title"));
        String newsfeed_img      = (dataFromForm.getParameter("newsfeed_img"));

        User user           = objectManager.userRepo.getOneUser(principal.getName());
        Organization org    = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());

        SimpleDateFormat sdf    = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSSSSS");
        Timestamp timestamp     = new Timestamp(System.currentTimeMillis());

        objectManager.newsRepo.insertNews(newsfeed_news, newsfeed_title, newsfeed_img, sdf.format(timestamp), org.getOrg_name());

        return "redirect:/newsfeed/" + org.getOrg_name();
    }

//   =======================  DELETE NEWS  =======================

    @PostMapping("/delete/newsfeed")
    public String deleteNews(WebRequest dataFromForm) {
        String newsfeed_id              = (dataFromForm.getParameter("newsfeed_id"));
        String user_mail                = (dataFromForm.getRemoteUser());

        int idParsed = Integer.parseInt(newsfeed_id);

        User user                   = objectManager.userRepo.getOneUser(user_mail);
        Organization organization   = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());

        objectManager.newsRepo.deleteNews(idParsed);

        return "redirect:/newsfeed/"+organization.getOrg_name();
    }
}
