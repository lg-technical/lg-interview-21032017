package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;
import com.wojciechkocik.usage.dto.DailyUsageResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utils for Course Daily Usage statistics
 *
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
public class DailyUsageUtils {

    /**
     * Groups list of {@link DailyUsage} entities and sum session spent time
     *
     * @param dailyUsages list of not grouped by date {@link DailyUsage} entities
     * @return grouped by date list of {@link DailyUsage} entities
     */
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
