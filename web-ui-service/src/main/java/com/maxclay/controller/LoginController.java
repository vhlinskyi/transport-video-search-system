package com.maxclay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Vlad Glinskiy
 */
@Controller
public class LoginController {

    // TODO change to modal
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    static String loginPage() {
        return "login";
    }

    // TODO change to modal
    @RequestMapping(value = "/sign-up", method = RequestMethod.GET)
    static String signupPage() {
        return "signup";
    }
}
