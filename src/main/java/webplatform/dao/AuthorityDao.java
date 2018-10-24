package webplatform.dao;

import webplatform.entity.Authority;

import java.util.List;

public interface AuthorityDao {
    List<Authority> getExistingAuthorities();

    Authority getAuthority(String auth);
}
