package com.goltsov.ylab.task.security;

import com.goltsov.ylab.task.exception.IllegalReadingChange;
import com.goltsov.ylab.task.model.user.Admin;
import com.goltsov.ylab.task.model.user.User;
import com.goltsov.ylab.task.service.UserServiceImpl;

public class AuthenticationManager {

    private final UserServiceImpl userService;

    private static User currentUser;

    public AuthenticationManager(UserServiceImpl userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User currentUser) {
        AuthenticationManager.currentUser = currentUser;
    }

    public void authenticate(String login, String password) throws IllegalAccessException {
        User existedUser = userService.getUserByEmail(login);

        if (existedUser.getPassword().equals(password)) {
            setCurrentUser(existedUser);
        } else {
            throw new IllegalAccessException("Login/email or password is incorrect. Try again");
        }
    }

    public boolean isUserAuthorized(User user) {
        return currentUser.equals(user);
    }

    public void exit() {
        setCurrentUser(null);
    }

    public boolean isUserAuthenticated() {
        if (getCurrentUser() == null) {
            throw new IllegalReadingChange("Вы должны аутентифицироваться дабы вносить показания.");
        } else if (getCurrentUser() instanceof Admin) {
            throw new IllegalReadingChange("Администратор не может изменять показания счётчиков!");
        }

        return true;
    }
}
