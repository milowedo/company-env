package webplatform.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import webplatform.entity.User;
import java.util.List;


@Repository
public class AccountDaoImpl implements AccountDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public AccountDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUser(int userId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(User.class, userId);
    }

    @Override
    public List<User> getAllUsers() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery(
                "from User order by first_name", User.class);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public User getUserByName(String username) {
        Session session = sessionFactory.getCurrentSession();
        javax.persistence.Query query = null;
        query = session.createQuery(
                "from User where username like :thePhrase", User.class)
                .setParameter("thePhrase", username);
        List<User> users = query.getResultList();
        if (users.isEmpty()) throw new UsernameNotFoundException(username);

        return users.get(0);
    }

    //get all the users that have the searched phrase in their email, username, first or last name
    @Override
    public List<User> searchUsers(String searchPhrase) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query;
        if(searchPhrase == null || searchPhrase.trim().length() == 0){
            query = currentSession.createQuery("from User", User.class);
        }else{
            query = currentSession.createQuery("from User where " +
                    "lower(username) like :thePhrase " +
                    "or lower(firstName) like :thePhrase " +
                    "or lower(lastName) like :thePhrase " +
                    "or lower(email) like :thePhrase", User.class)
                    .setParameter("thePhrase", "%"+searchPhrase+"%");
        }
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public void createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteUser(int theId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                .createQuery("delete from User where id=:userId")
                .setParameter("userId", theId);
        query.executeUpdate();
    }

}