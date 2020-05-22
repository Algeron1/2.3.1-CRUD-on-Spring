package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import web.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.*;

import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"users", "list"}, method = RequestMethod.GET)
    public String printUsers(ModelMap model) {
        List<User> users = userService.ListUsers();
        model.addAttribute("listUser", users);
        return "user-list";
    }

    @RequestMapping(value = "/delete=id{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:users";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newUser() {
        return "user-form";
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        userService.add(user);
        return "redirect:users";
    }

    @RequestMapping(value = "/edit=id{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-form";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:users";
    }
}