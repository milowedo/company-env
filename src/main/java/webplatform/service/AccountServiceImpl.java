package webplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webplatform.dao.AccountDao;
import webplatform.entity.User;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }


    //gets User from Dao, makes the object have a string version of authorities to be able to print it to the form later
    @Override
    @Transactional
    public User getUser(int userId) {
        User user = accountDao.getUser(userId);
        user.fillAuthorities_string();
        return user;
    }

    @Override
    @Transactional
    public User getUserByName(String username) {
        User user = accountDao.getUserByName(username);
        user.fillAuthorities_string();
        return user;
    }


    //just like for the single user
    @Override
    @Transactional
    public List<User> getAllUsers() {
        List<User> users = accountDao.getAllUsers();
        users.stream().forEach(User::fillAuthorities_string);
        return users;
    }

    @Override
    @Transactional
    public List<User> searchUsers(String searchPhrase) {
        List<User> users= accountDao.searchUsers(searchPhrase);
        users.stream().forEach(User::fillAuthorities_string);
        return users;
    }


    //encodes user password, creates authority objects from Strings for him to be able to save it into the database
    @Override
    @Transactional
    public void createUser(User user) {
        User tempUser = new User();

        if(user.getAuthorities_string() != null)
        user.getAuthorities_string().stream()
                .forEach(auth -> user.addAuthority(authorityService.getAuthority(auth)));

        tempUser.setId(user.getId());
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        tempUser.setEmail(user.getEmail());
        tempUser.setUsername(user.getUsername());
        tempUser.setAuthorities(user.getAuthorities());
        tempUser.setPassword(passwordEncoder.encode(user.getPassword()));

        accountDao.createUser(tempUser);
    }

    @Override
    @Transactional
    public void deleteUser(int theId) {
        accountDao.deleteUser(theId);
    }

}
