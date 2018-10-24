package webplatform.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import webplatform.entity.Authority;
import java.util.List;

@Repository
public class AuthorityDaoImpl implements AuthorityDao{

    private final SessionFactory sessionFactory;

    @Autowired
    public AuthorityDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Authority> getExistingAuthorities() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Authority> query =
                currentSession.createQuery("from Authority order by authority", Authority.class);
        return query.getResultList();
    }

    @Override
    public Authority getAuthority(String auth) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Authority> query =
                currentSession.createQuery("from Authority where authority like:parameter", Authority.class)
                .setParameter("parameter", auth);
        return query.getSingleResult();
    }
}
