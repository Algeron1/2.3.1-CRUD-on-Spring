package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role getRoleById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Role.class, id);
    }
}
