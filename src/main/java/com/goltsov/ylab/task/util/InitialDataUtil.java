package com.goltsov.ylab.task.util;

import com.goltsov.ylab.task.imitations.DatabaseImitation;
import com.goltsov.ylab.task.model.meter.Meter;
import com.goltsov.ylab.task.model.user.Admin;
import com.goltsov.ylab.task.model.user.Payer;
import com.goltsov.ylab.task.model.user.User;
import com.goltsov.ylab.task.service.ReadingServiceImpl;
import com.goltsov.ylab.task.service.UserServiceImpl;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialDataUtil {

    private final DatabaseImitation databaseImitation;
    private final ReadingServiceImpl readingService;

    public InitialDataUtil(ReadingServiceImpl readingService) {
        this.databaseImitation = DatabaseImitation.getInstance();
        this.readingService = readingService;
    }

    public void createData() {
        User user1 = new Payer("dima", "dima", "dima");
        User user2 = new Payer("dima2", "dima2", "dima2");

        databaseImitation.addUser(user1);
        databaseImitation.addUser(user2);

        Map<YearMonth, Integer> user1Readings = Map.of(YearMonth.of(2023, Month.NOVEMBER), 8, YearMonth.of(2023, Month.DECEMBER), 7);
        Map<YearMonth, Integer> user2Readings = Map.of(YearMonth.of(2023, Month.NOVEMBER), 8, YearMonth.of(2023, Month.DECEMBER), 7);

        readingService.getAllUserReadings(user1).values().add(user1Readings);
        readingService.getAllUserReadings(user2).values().add(user2Readings);
    }
}
