package com.goltsov.ylab.task.model.meter;

import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Meter {

    Map<YearMonth, Integer> getReadings();

    void addReading(Integer reading);

    String getMeterType();

    Map<YearMonth, Integer> getActualReading();

    Integer getParticularPeriodReading(YearMonth period);

    default Optional<YearMonth> findActualYearMonth(Set<YearMonth> yearMonths) {
        return yearMonths.stream()
                .max(YearMonth::compareTo);
    }
}
