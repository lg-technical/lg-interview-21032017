package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;

import java.util.List;

/**
 * Service for process list of {@link DailyUsage} entities handling crossing midnight session spent time case.
 *
 * eg. {@link DailyUsage} started at "2017-03-23T23:59:00.000+01:00[Europe/Warsaw]" and time session is 120 seconds,
 * so the half of this time will be happened the next day.
 *
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
public interface TimeSpentCrossMidnightService {
    List<DailyUsage> run(List<DailyUsage> dailyUsagesForCourse);
}
