package com.maxclay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Vlad Glinskiy
 */
@Controller
public class ProfileController {

    // TODO change to modal
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    static String profilePage() {
        return "profile";
    }

}
