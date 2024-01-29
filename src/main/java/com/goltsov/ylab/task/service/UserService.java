package com.goltsov.ylab.task.service;

import com.goltsov.ylab.task.model.user.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

}
