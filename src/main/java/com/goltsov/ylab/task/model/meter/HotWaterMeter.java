package com.goltsov.ylab.task.model.meter;

import com.goltsov.ylab.task.exception.IllegalReadingChange;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HotWaterMeter implements Meter {

    private final Map<YearMonth, Integer> readings;

    public HotWaterMeter() {
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
        return "ГВС";
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
