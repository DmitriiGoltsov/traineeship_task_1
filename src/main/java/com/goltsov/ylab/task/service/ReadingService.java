package com.goltsov.ylab.task.service;

import com.goltsov.ylab.task.model.meter.Meter;
import com.goltsov.ylab.task.model.user.User;

import java.time.YearMonth;
import java.util.Map;

public interface ReadingService {

    void addReading(User user, Integer reading, Integer meterType);
    Map<String, Map<YearMonth, Integer>> getUserActualReadings(User user);

    Map<String, Map<YearMonth, Integer>> getAllUserReadings(User user);
    Map<String, Integer> getAllReadingsForPeriod(YearMonth yearMonth, User user);
}
