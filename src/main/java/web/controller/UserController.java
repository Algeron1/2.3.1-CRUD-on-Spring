package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import web.service.*;
import web.model.*;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.listUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<List<User>>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        User user = userService.getUserById(id);
        try {
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/editUser/{id}")
    public ResponseEntity<?> editUser(
            @PathVariable(name = "id") Long id,
            @ModelAttribute User user,
            @RequestParam(value = "e_roles", defaultValue = "user") List<String> roles) {
        int roleId = 1; //роль по умолчанию
        if (roles.contains("admin")) {
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

    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addUser(@ModelAttribute User user, @RequestParam(value = "new_roles", defaultValue = "user") List<String> roles) {
        List<User> users = userService.listUsers();
        for(User iterateUser : users){
            if(user.getUserName().equals(iterateUser.getUserName())){
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        int roleId = 1; //роль по умолчанию
        if (roles.contains("admin")) {
            roleId = 2;
        }
        userService.add(user, roleId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") Long id) {
        User client = userService.getUserById(id);
        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/head")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}