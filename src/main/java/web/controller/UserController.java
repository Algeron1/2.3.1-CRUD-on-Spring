package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import web.model.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"users", "list"})
    public String printUsers(ModelMap model) {
        List<User> users = userService.ListUsers();
        model.addAttribute("listUser", users);
        return "user-list";
    }

    @GetMapping(value = "/delete=id{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user;
        if (id >= 0 &&  (user = userService.getUserById(id))!=null) {
                userService.delete(user);
                return "redirect:users";
        }
        model.addAttribute("message", "Ошибка, проверьте ID");
        return "error";
    }

    @GetMapping(value = "new")
    public String newUser() {
        return "user-form";
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        userService.add(user);
        return "redirect:users";
    }

    @GetMapping(value = "/edit=id{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user;
        if (id >= 0 &&  (user = userService.getUserById(id))!=null) {
                model.addAttribute("user", user);
                return "user-form";
        }
        model.addAttribute("message", "Ошибка, проверьте ID");
        return "error";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:users";
    }
}