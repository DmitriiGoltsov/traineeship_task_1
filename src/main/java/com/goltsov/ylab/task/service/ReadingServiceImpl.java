package com.goltsov.ylab.task.service;

import com.goltsov.ylab.task.imitations.DatabaseImitation;
import com.goltsov.ylab.task.model.meter.Meter;
import com.goltsov.ylab.task.model.user.User;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadingServiceImpl implements ReadingService {

    private final DatabaseImitation databaseImitation;

    public ReadingServiceImpl() {
        this.databaseImitation = DatabaseImitation.getInstance();
    }

    @Override
    public void addReading(User user, Integer reading, Integer meterType) {
        Long userId = user.getId();

        databaseImitation.addReadingToUser(userId, reading, meterType);
    }

    @Override
    public Map<String, Map<YearMonth, Integer>> getUserActualReadings(User user) {
        List<Meter> userMeters = databaseImitation.getMetersPerUser().get(user.getId());

        Map<String, Map<YearMonth, Integer>> result = new HashMap<>();

        for (Meter userMeter : userMeters) {
            result.put(userMeter.getMeterType(), userMeter.getActualReading());
        }

        return result;
    }

    private YearMonth getLastYearMonth(Map<YearMonth, Integer> readings) {
        return readings.keySet().stream()
                .min(YearMonth::compareTo)
                .orElseThrow(() -> new RuntimeException("Could not define the last reading period"));
    }

    private Map<String, Integer> getActualReadingPerMeter(YearMonth yearMonth, List<Meter> meters) {
        return meters.stream()
                .collect(Collectors.toMap(Meter::getMeterType, meter -> meter.getReadings().get(yearMonth)));
    }

    @Override
    public Map<String, Map<YearMonth, Integer>> getAllUserReadings(User user) {
        List<Meter> userMeters = databaseImitation.getMetersPerUser().get(user.getId());

        Map<String, Map<YearMonth, Integer>> result = new HashMap<>();

        for (Meter userMeter : userMeters) {
            result.put(userMeter.getMeterType(), userMeter.getReadings());
        }

        return result;
    }

    @Override
    public Map<String, Integer> getAllReadingsForPeriod(YearMonth yearMonth, User user) {
        List<Meter> meters = databaseImitation.getMetersPerUser().get(user.getId());

        Map<String, Integer> result = new HashMap<>();

        for (Meter meter : meters) {
            result.put(meter.getMeterType(), meter.getParticularPeriodReading(yearMonth));
        }

        return result;
    }

}
