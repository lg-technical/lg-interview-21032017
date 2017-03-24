package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.entity.CourseUsage;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface CourseUsageService {

    CourseUsage createNewCourseUsage(CourseUsageCreate courseUsageCreate);

    List<DailyUsageResponse> findDailyUsageForCourse(String courseId);
}
