package web.dao;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.*;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> ListUsers();

    void update(User user);

    void delete(User user);

    User getUserById(long id);

}
