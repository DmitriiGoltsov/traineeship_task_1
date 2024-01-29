package com.goltsov.ylab.task.pseudocontroller;

import com.goltsov.ylab.task.model.user.Payer;
import com.goltsov.ylab.task.security.AuthenticationManager;
import com.goltsov.ylab.task.service.UserServiceImpl;

import java.util.Scanner;

public class PseudoUserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    public PseudoUserController(UserServiceImpl userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public boolean addPayer(String name, String email, String password) {
        try {
            userService.addUser(new Payer(name, email, password));
        } catch (Exception e) {
            System.out.println("При попытке добавить плательщика произошла ошибка " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean authenticateUser() throws IllegalAccessException {
        try (Scanner authenticationScanner = new Scanner(System.in)) {
            System.out.println("Введите ваш логин/электронную почту:");
            String login = authenticationScanner.next();
            System.out.println("\nВведите ваш пароль:");
            String password = authenticationScanner.next();

            authenticationManager.authenticate(login, password);
        }

        return true;
    }
}
