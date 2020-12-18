package com.example.wearegantt.controller;

import com.example.wearegantt.model.LoginForm;
import com.example.wearegantt.model.Order;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.UserRepo;
import com.example.wearegantt.services.ObjectManager;
import com.example.wearegantt.services.PaypalService;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;


@Controller
public class LoginController {

    UserRepo userRepo = new UserRepo();

    ObjectManager objectManager = new ObjectManager();

    @Autowired
    PaypalService service;


//    =================================== GET ROUTES ==================

    @GetMapping("/login")
    private String login() {
        System.out.println("login test");
        return "login/login";
    }

    @GetMapping("/register")
    public String   register(HttpServletRequest request, Model model){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            System.out.println(inputFlashMap.get("error"));
            model.addAttribute("error", inputFlashMap.get("error"));
        }

        return "/login/register";
    }

    @GetMapping("/usertype")
    private String userType() {

        return "login/userType";
    }

    @GetMapping("/paypal")
    private String paypal() {

        return "paypal";
    }
//    =================================== POST ROUTES ==================

//    SAVE USER ==============

    @PostMapping("/saveUser")
    public RedirectView postUser(RedirectAttributes redirectAttributes, WebRequest dataFromForm) {
        String firstname = (dataFromForm.getParameter("firstname"));
        String lastname = (dataFromForm.getParameter("lastname"));
        String address = (dataFromForm.getParameter("address"));
        String phone = (dataFromForm.getParameter("phone"));
        String country = (dataFromForm.getParameter("country"));
        String zipcode = (dataFromForm.getParameter("zipcode"));
        String jobTitle = (dataFromForm.getParameter("jobTitle"));
        String password = (dataFromForm.getParameter("password"));
        String email = (dataFromForm.getParameter("email"));
        String role      = (dataFromForm.getParameter("role"));

        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        boolean userCheck = objectManager.userRepo.CheckUsernameExists(email);

        if(userCheck == true){
            redirectAttributes.addFlashAttribute("error", "Account Exists already");
            return new RedirectView("/register");
        } else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password1 = encoder.encode(password);

            userRepo.insertUser(email, password1, 1);
            User userObj = userRepo.getOneUser(email);

            userRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());
            userRepo.insertAuthUser(role, userObj.getUser_mail());

            return new RedirectView ("/login");
        }
    }

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

//    PAY ==============


    @GetMapping("/pay")
    public String payment(@ModelAttribute("order") Order order) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(), order.getIntent(), order.getDescription(), "http://localhost:1338/" + CANCEL_URL, "http://localhost:1338/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

//    LOGIN ?????????????????

    @RequestMapping(value = "/login/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "loginForm") LoginForm loginForm, Model model) {
        String user_mail = loginForm.getUser_mail();
        String user_password = loginForm.getUser_password();
        System.out.println("test");

        if (user_mail.equals(user_mail) && user_password.equals(user_password)) {
            return "main/index";
        }
        model.addAttribute("invalidCredentials", true);
        return "login/login";
    }

}



