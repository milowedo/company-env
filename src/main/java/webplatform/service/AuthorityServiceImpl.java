package webplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webplatform.dao.AuthorityDao;
import webplatform.entity.Authority;

import java.util.List;

@Service
public class AuthorityServiceImpl implements  AuthorityService {

    private final AuthorityDao authorityDao;

    @Autowired
    public AuthorityServiceImpl(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }


    @Override
    @Transactional
    public Authority getAuthority(String auth) {
        return authorityDao.getAuthority(auth);
    }

    @Override
    @Transactional
    public List<Authority> getExistingAuthorities() {
        return authorityDao.getExistingAuthorities();
    }
}
