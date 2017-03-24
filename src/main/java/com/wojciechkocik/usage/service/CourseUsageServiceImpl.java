package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsageForCourse;
import com.wojciechkocik.usage.entity.CourseUsage;
import com.wojciechkocik.usage.repository.CourseUsageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Slf4j
@Service
public class CourseUsageServiceImpl implements CourseUsageService {

    private final CourseUsageRepository courseUsageRepository;
    private final ModelMapper modelMapper;

    public CourseUsageServiceImpl(CourseUsageRepository courseUsageRepository, ModelMapper modelMapper) {
        this.courseUsageRepository = courseUsageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseUsage createNewCourseUsage(CourseUsageCreate courseUsageCreate) {
        CourseUsage courseUsage = modelMapper.map(courseUsageCreate, CourseUsage.class);
        return courseUsageRepository.save(courseUsage);
    }

    @Override
    public List<DailyUsageForCourse> findDailyUsageForCourse(String courseId) {
        List<DailyUsageForCourse> dailyUsagesForCourse = courseUsageRepository.findSpentTimeByCourseIdOnDate(courseId);

        List<DailyUsageForCourse> newDaysForMergeWithCurrentList = new ArrayList<>();

        dailyUsagesForCourse.forEach(p -> {
            ZonedDateTime dateTime = p.getDateTime();

            int dayBeforeSessionDuration = dateTime.getDayOfMonth();

            long sessionDuration = p.getTime();
            int dayAfterSessionDuration = dateTime.plusSeconds(sessionDuration).getDayOfMonth();

            if (dayAfterSessionDuration != dayBeforeSessionDuration) { //is crossed midnight

                ZonedDateTime zonedDateTimeMidnight = dateTime
                        .toLocalDate().atTime(LocalTime.MIDNIGHT).atZone(dateTime.getZone());
                long untilMidnightSeconds = dateTime.until(zonedDateTimeMidnight.plusDays(1), ChronoUnit.SECONDS);
                long secondsForNextDay = sessionDuration - untilMidnightSeconds;

                p.minusSpentSeconds(secondsForNextDay);

                //plus seconds for the next day
                List<DailyUsageForCourse> collect = dailyUsagesForCourse.stream().filter(f -> {
                    ZonedDateTime zonedDateTime = f.getDateTime().toLocalDate().atTime(LocalTime.MIDNIGHT).atZone(dateTime.getZone());
                    long untilHours = zonedDateTimeMidnight.until(zonedDateTime, ChronoUnit.HOURS);
                    if (untilHours > 0 && untilHours < 24) { //is next day
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

                if (!collect.isEmpty()) {
                    //add only for 1st element because in the next step it will be grouped
                    collect.get(0).plusSpentSeconds(secondsForNextDay);
                } else {
                    //there are no elements so next day must be created (midnight is started)
                    DailyUsageForCourse dailyUsageForCourseWithTimeFromDayBefore = new DailyUsageForCourse(
                            zonedDateTimeMidnight.plusDays(1),
                            secondsForNextDay
                    );
                    newDaysForMergeWithCurrentList.add(dailyUsageForCourseWithTimeFromDayBefore);
                }
            }
        });

        dailyUsagesForCourse.addAll(newDaysForMergeWithCurrentList);

        return dailyUsagesForCourse;
    }
}
