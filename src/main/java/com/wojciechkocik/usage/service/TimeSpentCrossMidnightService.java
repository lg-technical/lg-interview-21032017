package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
public interface TimeSpentCrossMidnightService {
    List<DailyUsage> run(List<DailyUsage> dailyUsagesForCourse);
}
