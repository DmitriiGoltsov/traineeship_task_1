package com.goltsov.ylab.task.pseudocontroller;

import com.goltsov.ylab.task.exception.IllegalReadingChange;
import com.goltsov.ylab.task.model.user.Admin;
import com.goltsov.ylab.task.security.AuthenticationManager;
import com.goltsov.ylab.task.service.ReadingServiceImpl;

import java.time.YearMonth;
import java.util.Map;

public class PseudoMeterController {

    private final ReadingServiceImpl readingService;
    private final AuthenticationManager manager;

    public PseudoMeterController(ReadingServiceImpl readingService, AuthenticationManager manager) {
        this.readingService = readingService;
        this.manager = manager;
    }

    public void postReading(int value, Integer meterType) {
        if (manager.getCurrentUser() == null) {
            throw new IllegalReadingChange("Вы должны аутентифицироваться дабы вносить показания.");
        } else if (manager.getCurrentUser() instanceof Admin) {
            throw new IllegalReadingChange("Администратор не может изменять показания счётчиков!");
        }

        readingService.addReading(manager.getCurrentUser(), value, meterType);
    }

    public Map<String, Map<YearMonth, Integer>> getUserActualReading() {
        if (manager.getCurrentUser() == null) {
            throw new IllegalReadingChange("Вы должны аутентифицироваться дабы получить актуальные показания.");
        } else if (manager.getCurrentUser() instanceof Admin) {
            throw new IllegalReadingChange("Администратор не может изменять показания счётчиков!");
        }

        return readingService.getUserActualReadings(manager.getCurrentUser());
    }

    public Map<String, Integer> getUserReadingForPeriod(YearMonth period) {
        try {
            manager.isUserAuthenticated();
        } catch (IllegalReadingChange e) {
            throw e;
        }

        return readingService.getAllReadingsForPeriod(period, manager.getCurrentUser());
    }
}
