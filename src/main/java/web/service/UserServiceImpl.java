package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.*;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(User user, int roleId) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getRoleById(roleId));
        if (roleId == 2) {
            roles.add(roleRepository.getRoleById(1));
        }
        user.setRoleSet(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user, int roleId) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getRoleById(roleId));
        if (roleId == 2) {
            roles.add(roleRepository.getRoleById(1));
        }
        user.setRoleSet(roles);
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.getUserName().equals(s)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User is not exist");
    }

}
