package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsage;
import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.entity.CourseUsage;
import com.wojciechkocik.usage.repository.CourseUsageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Slf4j
@Service
public class CourseUsageServiceImpl implements CourseUsageService {

    private final CourseUsageRepository courseUsageRepository;
    private final TimeSpentCrossMidnightService crossMidnightService;
    private final ModelMapper modelMapper;

    public CourseUsageServiceImpl(CourseUsageRepository courseUsageRepository, TimeSpentCrossMidnightServiceImpl crossMidnightService, ModelMapper modelMapper) {
        this.courseUsageRepository = courseUsageRepository;
        this.crossMidnightService = crossMidnightService;
        this.modelMapper = modelMapper;
    }

    /**
     * Implementation using {@link CourseUsageRepository} to store data
     *
     * @see CourseUsageService
     */
    @Override
    public CourseUsage createNewCourseUsage(CourseUsageCreate courseUsageCreate) {
        CourseUsage courseUsage = modelMapper.map(courseUsageCreate, CourseUsage.class);
        return courseUsageRepository.save(courseUsage);
    }

    /**
     * Implementation using {@link CourseUsageRepository} to finding data for course usage per day.
     *
     * Supports a situation, when the course session crossed the midnight.
     * Groups data by date and summing session spent time.
     *
     * @see CourseUsageService
     */
    @Override
    public List<DailyUsageResponse> findDailyUsageForCourse(String courseId) {
        List<DailyUsage> dailyUsagesForCourse = courseUsageRepository.findSpentTimeByCourseIdOnDate(courseId);
        List<DailyUsage> processedListByCrossedMidnightMechanism = crossMidnightService.divideDaysWithTimeSessionCrossedMidnight(dailyUsagesForCourse);

        return DailyUsageUtils.groupByDateAndSumTime(processedListByCrossedMidnightMechanism);
    }
}
