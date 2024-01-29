package com.goltsov.ylab.task.service;

import com.goltsov.ylab.task.imitations.DatabaseImitation;
import com.goltsov.ylab.task.model.user.User;
import com.goltsov.ylab.task.security.AuthenticationManager;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final DatabaseImitation databaseImitation;
    private final AuthenticationManager authenticationManager;


    public UserServiceImpl() {
        this.databaseImitation = DatabaseImitation.getInstance();
        this.authenticationManager = new AuthenticationManager(this);
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Override
    public User getUserById(Long id) {
        return databaseImitation.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return databaseImitation.getUsers();
    }

    public User getUserByEmail(String email) {
        return databaseImitation.getUserByEmail(email);
    }

    public void addUser(User user) {
        databaseImitation.addUser(user);
    }
}
