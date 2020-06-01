package web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @RequestMapping(value = "user/hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> messages = new ArrayList<>();
        List<User> users = new ArrayList<>();
        users.add(authUser);
        messages.add("Hello! " + authUser.getUserName());
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("allUsers", users);
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
