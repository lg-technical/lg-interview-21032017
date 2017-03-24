package com.wojciechkocik.usage.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
@Data
public class DailyUsage {
    public DailyUsage(ZonedDateTime dateTime, long time) {
        this.dateTime = dateTime;
        this.time = time;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime dateTime;

    private long time;

    public void minusSpentSeconds(long seconds){
        time -= seconds;
    }

    public void plusSpentSeconds(long seconds){
        time += seconds;
    }
}
