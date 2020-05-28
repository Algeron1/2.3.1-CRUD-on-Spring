package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    List<User> ListUsers();

    void update(User user);

    void delete(User user);

    User getUserById(long id);


}
