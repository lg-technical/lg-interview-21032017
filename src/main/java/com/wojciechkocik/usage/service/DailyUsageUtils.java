package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;
import com.wojciechkocik.usage.dto.DailyUsageResponse;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
public class DailyUsageUtils {

    public static List<DailyUsageResponse> groupByDateAndSumTime(List<DailyUsage> dailyUsages) {

        return dailyUsages.stream().collect(
                Collectors.groupingBy(DailyUsage::getSimpleDate, Collectors.summingLong(DailyUsage::getTime))
        )
        .entrySet().stream()
        .map(
                m -> new DailyUsageResponse(m.getKey(), m.getValue())
        )
        .collect(Collectors.toList()
        );
    }
}
