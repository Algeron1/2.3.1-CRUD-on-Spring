package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LoginController {
    @RequestMapping(value = "user/hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("authUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
