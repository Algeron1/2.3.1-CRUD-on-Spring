package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {

    void add(User user, int roleId);

    List<User> ListUsers();

    void update(User user, int role);

    void delete(User user);

    User getUserById(long id);


}
