package com.example.Enterprise_Portfolio_Instructions.Service;

import com.example.Enterprise_Portfolio_Instructions.AuthenticationService.User;
import com.example.Enterprise_Portfolio_Instructions.Dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

       @Autowired
       private UserDao userDao;

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public List<User> getUser() {
        return userDao.findAll();
    }

    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
