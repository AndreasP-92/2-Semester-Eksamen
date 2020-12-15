package com.example.wearegantt.controller;

import com.example.wearegantt.model.LoginForm;
import com.example.wearegantt.model.Order;
import com.example.wearegantt.model.User;
import com.example.wearegantt.repository.UserRepo;
import com.example.wearegantt.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@Controller
public class LoginController {

    UserRepo userRepo = new UserRepo();

    @Autowired
    PaypalService service;


//    =================================== GET ROUTES ==================

    @GetMapping("/login")
    private String login() {
        System.out.println("login test");
        return "login/login";
    }

    @GetMapping("/register")
    private String register() {

        return "login/register";
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
    public String postUser(WebRequest dataFromForm) {
        String firstname = (dataFromForm.getParameter("firstname"));
        String lastname = (dataFromForm.getParameter("lastname"));
        String address = (dataFromForm.getParameter("address"));
        String phone = (dataFromForm.getParameter("phone"));
        String country = (dataFromForm.getParameter("country"));
        String zipcode = (dataFromForm.getParameter("zipcode"));
        String jobTitle = (dataFromForm.getParameter("jobTitle"));
        String password = (dataFromForm.getParameter("password"));
        String email = (dataFromForm.getParameter("email"));


        int zipParsed = Integer.parseInt(zipcode);
        int phoneParsed = Integer.parseInt(phone);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);

        userRepo.insertUser(email, password, 1);
        User userObj = userRepo.getOneUser(email);

        System.out.println(userObj);

        userRepo.insertProfile(firstname, lastname, address, phoneParsed, country, zipParsed, jobTitle, userObj.getUser_id());

        userRepo.insertAuthUser("ROLE_USER", userObj.getUser_mail());



        return "redirect:/";
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



