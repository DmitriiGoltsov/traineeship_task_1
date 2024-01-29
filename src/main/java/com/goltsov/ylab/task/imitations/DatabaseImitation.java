package com.goltsov.ylab.task.imitations;

import com.goltsov.ylab.task.exception.IllegalReadingChange;
import com.goltsov.ylab.task.exception.UserAlreadyExist;
import com.goltsov.ylab.task.exception.UserNotFoundException;
import com.goltsov.ylab.task.model.meter.Meter;
import com.goltsov.ylab.task.model.user.Admin;
import com.goltsov.ylab.task.model.user.Payer;
import com.goltsov.ylab.task.model.user.User;
import com.goltsov.ylab.task.util.MeterUtil;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DatabaseImitation {

    private static Long USER_COUNTER = 1L;

    private static DatabaseImitation instance;

    private final Map<Long, List<Meter>> metersPerUser;

    private final List<User> users;

    private DatabaseImitation() {
        this.metersPerUser = new HashMap<>();
        this.users = new ArrayList<>();

        User adminUser = new Admin("admin", "admin", "admin");
        adminUser.setId(USER_COUNTER++);

        users.add(adminUser);
        metersPerUser.put(adminUser.getId(), null);

        User user1 = new Payer("dim", "dim", "dim");
        user1.setId(USER_COUNTER++);

        users.add(user1);
        metersPerUser.put(user1.getId(), MeterUtil.createMetersForUser());

        metersPerUser.get(user1.getId()).get(0).getReadings().put(YearMonth.of(2023, Month.NOVEMBER), 8);
        metersPerUser.get(user1.getId()).get(0).getReadings().put(YearMonth.of(2023, Month.DECEMBER), 7);
    }

    public static DatabaseImitation getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DatabaseImitation();
        }
        return instance;
    }

    public Map<Long, List<Meter>> getMetersPerUser() {
        return instance.metersPerUser;
    }

    public long addUser(User user) {
        if (!doesUserAlreadyExist(user)) {
            throw new UserAlreadyExist("User with login " + user.getEmail()
                    + " already exist. You cannot add their meters twice");
        }
        user.setId(USER_COUNTER++);
        metersPerUser.put(user.getId(), MeterUtil.createMetersForUser());
        users.add(user);

        return USER_COUNTER;
    }

    public void addReadingToUser(Long userId, Integer value, Integer meterType) {
        List<Meter> meters = metersPerUser.get(userId);

        YearMonth currentYearMonth = YearMonth.now();

        var readings = meters.get(meterType).getReadings();

        if (!readings.containsKey(currentYearMonth)) {
            readings.put(currentYearMonth, value);
        } else {
            throw new IllegalReadingChange("Вы уже вносили показания по "
                    + meters.get(meterType).getClass().getCanonicalName() + " за период " + currentYearMonth
                    + ". Показания можно вносить лишь один раз в месяц");
        }
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));
    }

    public List<User> getUsers() {
        return instance.users;
    }

    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with login/email " + email + " not found"));
    }

    private boolean doesUserAlreadyExist(User userToCheck) {
        String login = userToCheck.getEmail();
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getEmail().equals(login))
                .findAny();

        return optionalUser.isEmpty();
    }
}
