package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import web.repository.RoleRepository;
import web.service.*;
import web.model.*;

import javax.persistence.Entity;
import java.util.*;


@RestController("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    RoleRepository roleRepository;

    private Role roleForAdd=null;

    private Role roleForUpdate=null;


    @GetMapping(value = "api/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.listUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<List<User>>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "api/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        User user = userService.getUserById(id);
        try {
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "api/editUser/{id}")
    public ResponseEntity<?> editUser(@RequestBody User user) {
        int roleId = 1; //роль по умолчанию
        if (roleForUpdate.toString().equalsIgnoreCase("admin ")) {
            roleId = 2;
        }
        try {
            userService.update(user, roleId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new  ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "api/addUser")
    @ResponseBody
    public ResponseEntity<?> addUser(@RequestBody User user) {
        List<User> users = userService.listUsers();
        for(User iterateUser : users){
            if(user.getUserName().equals(iterateUser.getUserName())){
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        int roleId = 1; //роль по умолчанию
        if (roleForAdd.toString().equalsIgnoreCase("admin")) {
            roleId = 2;
        }
        userService.add(user, roleId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "api/user/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") Long id){
        User client = userService.getUserById(id);
        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/head")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "api/roleForAdd")
    private ResponseEntity<Role> getRoleForAdd(@RequestBody Role role) {
       roleForAdd=role;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "api/roleForUpdate")
    private ResponseEntity<Role> getRoleForUpdate(@RequestBody Role role) {
        roleForUpdate=role;
        return new ResponseEntity<>(HttpStatus.OK);
    }
}