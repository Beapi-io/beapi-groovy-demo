package demo.application.service

import java.util.Optional;
import demo.application.domain.User

interface IUser {
    ArrayList<User> getAllUsers()
    Optional<User> findById(int id)
    User findByEmail(String email)
    User findByUsername(String username)
    User save(User usr)
    void deleteById(int id)
    User bootstrapUser(User usr)
}
