package com.wojciechkocik.usage.dto;

import lombok.Data;

/**
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
@Data
public class DailyUsageResponse {
    public DailyUsageResponse(String dateTime, long time) {
        this.dateTime = dateTime;
        this.time = time;
    }

    private String dateTime;

    private long time;
}
