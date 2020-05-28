package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import web.model.*;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = {"admin/list", "list"})
    public String printUsers(ModelMap model) {
        List<User> users = userService.ListUsers();
        model.addAttribute("listUser", users);
        return "user-list";
    }

    @GetMapping(value = "admin/delete=id{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user;
        if (id >= 0 && (user = userService.getUserById(id)) != null) {
            userService.delete(user);
            return "redirect:list";
        }
        model.addAttribute("message", "Ошибка, проверьте ID");
        return "error";
    }

    @GetMapping(value = "admin/new")
    public String newUser() {
        return "user-form";
    }

    @RequestMapping(value = "admin/insert", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, ModelMap model) {
        User userFromDb = (User) userDetailsService.loadUserByUsername(user.getUserName());
        if (userFromDb != null) {
            model.addAttribute("message", "Такой пользователь уже существует");
            return "error";
        }
        userService.add(user);
        return "redirect:list";
    }

    @GetMapping(value = "admin/edit=id{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user;
        if (id >= 0 && (user = userService.getUserById(id)) != null) {
            model.addAttribute("user", user);
            return "user-form";
        }
        model.addAttribute("message", "Ошибка, проверьте ID");
        return "error";
    }

    @RequestMapping(value = "admin/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:list";
    }
}