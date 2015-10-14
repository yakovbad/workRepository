package ClassWork.repositories;


import ClassWork.Entities.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    List<User> findUserByFirstName(String firstName);

    User findUserById(int id);
}
