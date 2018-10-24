package webplatform.service;

import org.springframework.stereotype.Service;
import webplatform.entity.User;

import java.util.List;

@Service
public interface AccountService {

    User getUser(int userId);

    List<User> getAllUsers();

    void createUser(User user);

    void deleteUser(int theId);

    List<User> searchUsers(String searchPhrase);

    User getUserByName(String username);
}
