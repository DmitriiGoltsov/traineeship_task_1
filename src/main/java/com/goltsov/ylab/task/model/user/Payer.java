package com.goltsov.ylab.task.model.user;

import com.goltsov.ylab.task.model.meter.Meter;
import com.goltsov.ylab.task.util.MeterUtil;

import java.util.List;

public class Payer extends User {

    private final List<Meter> meters;

    private final Role role;

    public Payer(String name, String email, String password) {
        super(name, email, password);
        this.meters = MeterUtil.createMetersForUser();;
        this.role = Role.payer;
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public Role getRole() {
        return role;
    }
}
