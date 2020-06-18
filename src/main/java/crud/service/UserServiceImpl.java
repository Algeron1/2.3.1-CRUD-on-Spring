package crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import crud.model.*;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.codec.binary.Base64;

import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    public static final String REST_SERVICE_URI = "http://localhost:8080/";

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private HttpHeaders getHeaders() {
        String plainCredentials = "admin@admin.ru:1";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }


    @Override
    public void add(User user, int roleId) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = getRoleById(roleId);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<User>(user, getHeaders());
        HttpEntity<Role> roleRequest = new HttpEntity<Role>(role, getHeaders());
        restTemplate.postForLocation(REST_SERVICE_URI + "api/roleForAdd", roleRequest, Role.class);
        restTemplate.postForLocation(REST_SERVICE_URI + "api/addUser", request, User.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "api/users/", HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();
        List<User> userList = new ArrayList<>();
        Set<Role> roleSet = new HashSet<>();
        if (usersMap != null) {
            for (LinkedHashMap<String, Object> map : usersMap) {
                User user = new User();
                user.setAge((int) map.get("age"));
                user.setId((Integer) map.get("id"));
                user.setFirstName((String) map.get("firstName"));
                user.setLastName((String) map.get("lastName"));
                user.setPassword((String) map.get("password"));
                user.setUserName((String) map.get("userName"));
                roleSet = getUserRoleSet(user.getId());
                user.setRoles(roleSet);
                userList.add(user);
            }
        } else {
            System.out.println("No user exist----------");
        }
        return userList;
    }

    @Override
    public void update(User user, int roleId) {
        Role role = getRoleById(roleId);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<User>(user, getHeaders());
        HttpEntity<Role> roleRequest = new HttpEntity<Role>(role, getHeaders());
        restTemplate.postForLocation(REST_SERVICE_URI + "api/roleForUpdate", roleRequest, Role.class);
        restTemplate.postForLocation(REST_SERVICE_URI + "api/editUser/" + user.getId(), request, User.class);
    }

    @Override
    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Long> request = new HttpEntity<Long>(id, getHeaders());
        restTemplate.postForLocation(REST_SERVICE_URI + "api/deleteUser/" + id, request, Long.class);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI + "api/user/" + id, HttpMethod.GET, request, User.class);
        User user = response.getBody();
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> users = listUsers();
        for (User user : users) {
            if (user.getUserName().equals(s)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User is not exist");
    }

    private Role getRoleById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Role> response = restTemplate.exchange(REST_SERVICE_URI + "api/role/" + id, HttpMethod.GET, request, Role.class);
        Role role = response.getBody();
        return role;
    }

    private Set<Role> getUserRoleSet(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "api/usersRole/" + id, HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> roleSet = (List<LinkedHashMap<String, Object>>) response.getBody();
        Set<Role> userRoles = new HashSet<>();
        if (roleSet != null) {
            for (LinkedHashMap<String, Object> map : roleSet) {
                Role role = new Role();
                role.setId((Integer) map.get("id"));
                role.setName((String) map.get("name"));
                userRoles.add(role);
            }
        } else {
            System.out.println("No role exist----------");
        }
        return userRoles;
    }
}
