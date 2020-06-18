package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.Set;

@RestController("api/")
public class RoleRestController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @GetMapping(value = "api/role/{id}")
    public ResponseEntity<Role> read(@PathVariable(name = "id") Long id) {
        Role role = roleService.getRoleById(id);
        return role != null
                ? new ResponseEntity<>(role, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/usersRole/{id}")
    public ResponseEntity<Set> userRoleSet(@PathVariable(name = "id") Long id){
        User user = userService.getUserById(id);
        Set<Role> roles = user.getRoleSet();
        return roles != null
                ? new ResponseEntity<>(roles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
