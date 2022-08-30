package demo.application.service

import demo.application.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import demo.application.domain.User

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired

@Service
public class UserService implements IUser {

    UserRepository userrepo;

    @Autowired
    public UserService(UserRepository userrepo) {
        this.userrepo = userrepo;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        // TODO Auto-generated method stub
        return userrepo.findAll();
    }
    @Override
    public Optional<User> findById(int id) {
        // TODO Auto-generated method stub
        return userrepo.findById(id);
    }
    @Override
    public User findByEmail(String email) {
        // TODO Auto-generated method stub
        return userrepo.findByEmail(email);
    }
    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return userrepo.findByUsername(username);
    }
    @Override
    public User save(User usr) {
        // TODO Auto-generated method stub
        return userrepo.save(usr);
    }

    @Override
    public void deleteById(int id) {
        // TODO Auto-generated method stub
        userrepo.deleteById(id);
    }

    @Override
    public User bootstrapUser(User usr) {
        // TODO Auto-generated method stub
        return userrepo.save(usr)
    }

}
