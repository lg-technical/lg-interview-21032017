package com.wojciechkocik.usage.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * @author Wojciech Kocik
 * @since 23.03.2017
 */
@Data
public class DailyUsageForUser {

    public DailyUsageForUser(ZonedDateTime dateTime, long time) {
        this.dateTime = dateTime;
        this.time = time;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime dateTime;

    private long time;
}
