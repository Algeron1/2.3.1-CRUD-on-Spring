package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDao;
import web.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import web.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = {"admin/list", "list"})
    public String printUsers(ModelMap model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authUser", authUser);
        model.addAttribute("listUser", userService.ListUsers());
        //пустой юзер для добавления
        User user = new User();
        model.addAttribute("user", user);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(1));
        roles.add(roleService.getRoleById(2));
        model.addAttribute("roles", roles);
        return "user-list";
    }

    @RequestMapping(value = "admin/delete=id{id}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user;
        if (id >= 0 && (user = userService.getUserById(id)) != null) {
            userService.delete(user);
            return "redirect:list";
        }
        model.addAttribute("message", "Ошибка, проверьте ID");
        return "error";
    }

    /*метод на удаление
    @GetMapping(value = "admin/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-form";
    }
    */

    @RequestMapping(value = "admin/insert", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("id") int role, ModelMap model) {
        try {
            User userFromDb = (User) userDetailsService.loadUserByUsername(user.getUserName());
            if (userFromDb != null) {
                model.addAttribute("message", "Такой пользователь уже существует");
                return "error";
            }
        }
        catch (UsernameNotFoundException e) {
            userService.add(user, role);
        }
        return "redirect:list";
    }

    @RequestMapping(value = "admin/edit=id{id}", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user2") User user, @RequestParam(name = "checkboxRoles") String[] checkboxRoles) {
            userService.update(user, Integer.parseInt(checkboxRoles[1]));
            return "redirect:list";

    }

    /* метод на удаление
    @RequestMapping(value = "admin/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user2") User user) {
       // userService.update(user, 1);
        return "redirect:list";
    }
     */
}