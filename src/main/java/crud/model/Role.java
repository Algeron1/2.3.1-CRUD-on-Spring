package crud.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")

public class Role implements GrantedAuthority {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        if (getName().equals("ROLE_USER")) {
            return "USER ";
        } else if (getName().equals("ROLE_ADMIN")) {
            return "ADMIN ";
        } else return getName();
    }
}