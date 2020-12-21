package com.example.wearegantt.controller;



import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.UserRepo;
import com.example.wearegantt.services.ObjectManager;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Map;


@Controller
public class LoginController {

//    =================== SERVICES ==================

    UserRepo userRepo = new UserRepo();

    ObjectManager objectManager = new ObjectManager();

//   =================================================================== GET CONTROLLER ==========================================================================

// ============== LOGIN ==============

    @GetMapping("/login")
    private String login(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        System.out.println("INPUT============="+inputFlashMap);

        if (inputFlashMap != null) {
            model.addAttribute("success", inputFlashMap.get("success"));
        }
        return "login/login";
    }

// ============== REGISTER ==============

    @GetMapping("/register")
    public String  register(HttpServletRequest request, Model model){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        return "/login/register";
    }

// ============== USER TYPE SELECTION ==============

    @GetMapping("/usertype")
    private String userType() {

        return "login/userType";
    }

// ============== USER TYPE SELECTION ==============

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

//   =================================================================== POST CONTROLLER ==========================================================================

//   ============== SAVE USER ==============

    @PostMapping("/saveUser")
    public RedirectView postUser(RedirectAttributes redirectAttributes, WebRequest dataFromForm) {
        String firstname    = (dataFromForm.getParameter("firstname"));
        String lastname     = (dataFromForm.getParameter("lastname"));
        String address      = (dataFromForm.getParameter("address"));
        String phone        = (dataFromForm.getParameter("phone"));
        String country      = (dataFromForm.getParameter("country"));
        String zipcode      = (dataFromForm.getParameter("zipcode"));
        String jobTitle     = (dataFromForm.getParameter("jobTitle"));
        String password     = (dataFromForm.getParameter("password"));
        String email        = (dataFromForm.getParameter("email"));
        String role         = (dataFromForm.getParameter("role"));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        // CHECKS IF USER EXISTS IN DB
        boolean userCheck = objectManager.userRepo.checkUsernameExists(email);

        //  IF USER EXISTS RETURN Account Exists Allready
        if(userCheck == true){
            redirectAttributes.addFlashAttribute("error", "Account Exists Already");
            return new RedirectView("/register");

        // IF USER DOESN'T EXIST
        } else{

            //  ENCRYPTS PASSWORD
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password1 = encoder.encode(password);

            userRepo.insertUser(email, password1, 1);
            User userObj = userRepo.getOneUser(email);

            userRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());
            userRepo.insertAuthUser(role, userObj.getUser_mail());

            //   VALIDATE USERTYPE
            if(role.equals("ROLE_NORMALUSER")){
                objectManager.userRepo.insertPayment(4, timestamp, userObj.getUser_id());
            }
            if(role.equals("ROLE_SUPERUSER")){
                objectManager.userRepo.insertPayment(12, timestamp, userObj.getUser_id());
            }
            redirectAttributes.addFlashAttribute("success", "Success!");
            return new RedirectView ("/login");
        }
    }
}



