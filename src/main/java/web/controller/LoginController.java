package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String printWelcome() {
        return "user-list";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String printUser() {
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
