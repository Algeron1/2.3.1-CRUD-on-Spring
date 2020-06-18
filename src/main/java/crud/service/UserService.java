package crud.service;

import crud.model.User;

import java.util.List;

public interface UserService {

    void add(User user, int roleId);

    List<User> listUsers();

    void update(User user, int role);

    void delete(Long id);

    User getUserById(long id);

}
