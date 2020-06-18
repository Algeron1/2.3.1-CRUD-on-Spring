package crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String printWelcome() {
        return "adminPage";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String printUser() {
        return "userPage";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
