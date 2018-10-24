package webplatform.dao;

import webplatform.entity.User;
import java.util.List;

public interface AccountDao{

    User getUser(int userId);

    List<User> getAllUsers();

    void createUser(User user);

    void deleteUser(int id);

    List<User> searchUsers(String searchPhrase);

    User getUserByName(String username);
}
