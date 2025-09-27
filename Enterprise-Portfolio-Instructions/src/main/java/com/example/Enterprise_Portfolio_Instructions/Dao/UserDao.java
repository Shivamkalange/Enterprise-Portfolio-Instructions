package com.example.Enterprise_Portfolio_Instructions.Dao;

import com.example.Enterprise_Portfolio_Instructions.AuthenticationService.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {

    User findByUsername(String username);
}
