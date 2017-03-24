package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.entity.CourseUsage;

import java.util.List;

/**
 * Performs business logic for persist and fetch Course Usage data related to courseId.
 *
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface CourseUsageService {

    /**
     * Persists new {@link CourseUsage} entity
     *
     * @param courseUsageCreate dto model which will be mapped to db entity
     * @return saved entity with persisted id field
     */
    CourseUsage createNewCourseUsage(CourseUsageCreate courseUsageCreate);

    /**
     * Getting statistics for course usage per day
     *
     * @param courseId persisted course identity
     * @return return length of time spent by all users on course every day (in seconds)
     */
    List<DailyUsageResponse> findDailyUsageForCourse(String courseId);
}
