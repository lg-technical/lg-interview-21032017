package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
public class DailyUsageUtils {

    public static List<DailyUsage> groupByDateAndSumTime(List<DailyUsage> dailyUsages) {

        return dailyUsages.stream().collect(
                Collectors.groupingBy(DailyUsage::getDateTime, Collectors.summingLong(DailyUsage::getTime))
        )
        .entrySet().stream()
        .map(
                m -> new DailyUsage(m.getKey(), m.getValue()))
        .collect(Collectors.toList()
        );
    }
}
