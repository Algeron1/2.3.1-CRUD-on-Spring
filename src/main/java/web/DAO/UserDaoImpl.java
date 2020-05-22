package web.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;
import web.model.*;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    @Transactional
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> ListUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.load(User.class, id);
        session.delete(user);
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(User.class, id);
    }
}
