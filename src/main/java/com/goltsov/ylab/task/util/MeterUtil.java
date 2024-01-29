package com.goltsov.ylab.task.util;

import com.goltsov.ylab.task.model.meter.ColdWaterMeter;
import com.goltsov.ylab.task.model.meter.HeatingMeter;
import com.goltsov.ylab.task.model.meter.HotWaterMeter;
import com.goltsov.ylab.task.model.meter.Meter;

import java.util.List;

public class MeterUtil {

    public static List<Meter> createMetersForUser() {
        return List.of(new ColdWaterMeter(), new HotWaterMeter(), new HeatingMeter());
    }
}
