package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wojciech Kocik
 * @since 24.03.2017
 */
@Service
public class TimeSpentCrossMidnightServiceImpl implements TimeSpentCrossMidnightService {

    @Override
    public List<DailyUsage> run(List<DailyUsage> dailyUsagesForCourse) {

        List<DailyUsage> newDaysForMergeWithCurrentList = new ArrayList<>();

        dailyUsagesForCourse.forEach(p -> {
            ZonedDateTime dateTime = p.getDateTime();

            int dayBeforeSessionDuration = dateTime.getDayOfMonth();

            long sessionDuration = p.getTime();
            int dayAfterSessionDuration = dateTime.plusSeconds(sessionDuration).getDayOfMonth();

            if (isCrossedMidnight(dayBeforeSessionDuration, dayAfterSessionDuration)) {

                ZonedDateTime zonedDateTimeMidnight = getMidnightOfDate(dateTime);
                long untilMidnightSeconds = dateTime.until(zonedDateTimeMidnight.plusDays(1), ChronoUnit.SECONDS);
                long secondsForNextDay = sessionDuration - untilMidnightSeconds;

                p.minusSpentSeconds(secondsForNextDay);

                List<DailyUsage> collect = filterDailyUsagesNextDays(dailyUsagesForCourse, dateTime);

                if (!collect.isEmpty()) {
                    //add only for 1st element because in the next step it will be grouped
                    collect.get(0).plusSpentSeconds(secondsForNextDay);
                } else {
                    //there are no elements so next day must be created (midnight is started)
                    DailyUsage dailyUsageForCourseWithTimeFromDayBefore = new DailyUsage(
                            zonedDateTimeMidnight.plusDays(1),
                            secondsForNextDay
                    );
                    newDaysForMergeWithCurrentList.add(dailyUsageForCourseWithTimeFromDayBefore);
                }
            }
        });
        return newDaysForMergeWithCurrentList;
    }

    private boolean isCrossedMidnight(int dayBeforeSessionDuration, int dayAfterSessionDuration) {
        return dayAfterSessionDuration != dayBeforeSessionDuration;
    }

    private ZonedDateTime getMidnightOfDate(ZonedDateTime dateTime) {
        return dateTime
                .toLocalDate().atTime(LocalTime.MIDNIGHT).atZone(dateTime.getZone());
    }

    private List<DailyUsage> filterDailyUsagesNextDays(List<DailyUsage> dailyUsagesForCourse, ZonedDateTime dateTime) {
        return dailyUsagesForCourse.stream().filter(f -> {
            ZonedDateTime zonedDateTime = f.getDateTime().toLocalDate().atTime(LocalTime.MIDNIGHT).atZone(dateTime.getZone());
            long untilHours = getMidnightOfDate(dateTime).until(zonedDateTime, ChronoUnit.HOURS);
            if (untilHours > 0 && untilHours < 24) { //is next day
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }


}
