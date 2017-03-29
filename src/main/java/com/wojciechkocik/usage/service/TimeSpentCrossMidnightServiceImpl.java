package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link TimeSpentCrossMidnightService} using java 8 features
 *
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
@Service
public class TimeSpentCrossMidnightServiceImpl implements TimeSpentCrossMidnightService {

    @Override
    public List<DailyUsage> divideDaysWithTimeSessionCrossedMidnight(List<DailyUsage> dailyUsagesForCourse) {

        List<DailyUsage> dividedDailyUsages = new ArrayList<>();

        dailyUsagesForCourse.forEach(dailyUsage -> {
            ZonedDateTime timeDateStarted = dailyUsage.getDateTime();

            long sessionTime = dailyUsage.getTime();

            long epochBeforeSession = timeDateStarted.toEpochSecond();
            long epochAfterSession = timeDateStarted.plusSeconds(sessionTime).toEpochSecond();
            long epochDiff = epochAfterSession - epochBeforeSession;

            long untilMidnight = timeDateStarted.until(getMidnightOfDate(timeDateStarted.plusDays(1)), ChronoUnit.SECONDS);
            long restEpoch = epochDiff - untilMidnight;

            if(sessionTime <= 0){
                return;
            }
            if (sessionTime <= untilMidnight) {
                dividedDailyUsages.add(dailyUsage);
                return;
            }

            int daysSessionExceptFirstAndLastDay = (int) Duration.ofSeconds(restEpoch).toDays();
            int wholeDaysSession = daysSessionExceptFirstAndLastDay + 2;

            long restEpochTemp = restEpoch;

            long secondsInDay = 60 * 60 * 24;

            for (int i = 0; i < wholeDaysSession; i++) {
                DailyUsage dailyUsageTemp = null;
                if (i == 0) { //first day
                    dailyUsageTemp = new DailyUsage(timeDateStarted.plusDays(i), untilMidnight);
                } else {
                    ZonedDateTime midnightOfNextDay = getMidnightOfDate(timeDateStarted.plusDays(i));
                    if (restEpochTemp > secondsInDay) {
                        dailyUsageTemp = new DailyUsage(midnightOfNextDay, secondsInDay);
                        restEpochTemp -= secondsInDay;
                    } else {
                        dailyUsageTemp = new DailyUsage(midnightOfNextDay, restEpochTemp);
                    }
                }
                dividedDailyUsages.add(dailyUsageTemp);
            }
        });
        return dividedDailyUsages;
    }

    private ZonedDateTime getMidnightOfDate(ZonedDateTime dateTime) {
        return dateTime
                .toLocalDate().atTime(LocalTime.MIDNIGHT).atZone(dateTime.getZone());
    }
}
