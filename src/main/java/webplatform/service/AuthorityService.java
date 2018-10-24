package webplatform.service;

import org.springframework.stereotype.Service;
import webplatform.entity.Authority;

import java.util.List;

@Service
public interface AuthorityService {

    List<Authority> getExistingAuthorities();

    Authority getAuthority(String auth);
}
