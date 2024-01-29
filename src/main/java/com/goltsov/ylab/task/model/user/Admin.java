package com.goltsov.ylab.task.model.user;

public class Admin extends User {

    private Role role;

    public Admin(String name, String email, String password) {
        super(name, email, password);
        this.role = Role.admin;
    }

    public Role getRole() {
        return role;
    }
}
