package webplatform.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webplatform.config.CustomUserDetails;
import webplatform.entity.User;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public UserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userLoaded = accountService.getUserByName(username);
        List<SimpleGrantedAuthority> auth;
        auth = userLoaded.getGrantedAuthorities();
        return new CustomUserDetails(userLoaded, auth);
    }
}
