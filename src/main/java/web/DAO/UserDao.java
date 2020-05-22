package web.DAO;

import web.model.*;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> ListUsers();

    void update(User user);

    void delete(long id);

    User getUserById(long id);
}
