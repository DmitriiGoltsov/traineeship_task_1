package com.goltsov.ylab.task.model.meter;

import com.goltsov.ylab.task.exception.IllegalReadingChange;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ColdWaterMeter implements Meter {

    private final Map<YearMonth, Integer> readings;

    public ColdWaterMeter() {
        this.readings = new HashMap<>();
    }

    @Override
    public Map<YearMonth, Integer> getReadings() {
        return this.readings;
    }

    @Override
    public void addReading(Integer reading) {
        YearMonth currentYearMonth = YearMonth.now();

        if (!readings.containsKey(currentYearMonth)) {
            readings.put(currentYearMonth, reading);
        } else {
            throw new IllegalReadingChange("Вы уже передали показания приборов учёта в этом месяце. "
                    + "Переданные ранее показания изменению не подлежат");
        }
    }

    @Override
    public String getMeterType() {
        return "ХВС";
    }

    @Override
    public Map<YearMonth, Integer> getActualReading() {
        Optional<YearMonth> optionalActualYearMonth = findActualYearMonth(readings.keySet());

        return optionalActualYearMonth.map(yearMonth -> Map.of(yearMonth, readings.get(yearMonth))).orElseGet(Map::of);
    }

    @Override
    public Integer getParticularPeriodReading(YearMonth period) {
        return readings.get(period);
    }
}
