package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsage;
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

    @Override
    public CourseUsage createNewCourseUsage(CourseUsageCreate courseUsageCreate) {
        CourseUsage courseUsage = modelMapper.map(courseUsageCreate, CourseUsage.class);
        return courseUsageRepository.save(courseUsage);
    }

    @Override
    public List<DailyUsage> findDailyUsageForCourse(String courseId) {
        List<DailyUsage> dailyUsagesForCourse = courseUsageRepository.findSpentTimeByCourseIdOnDate(courseId);
        List<DailyUsage> processedListByCrossedMidnightMechanism = crossMidnightService.run(dailyUsagesForCourse);
        dailyUsagesForCourse.addAll(processedListByCrossedMidnightMechanism);

        //TODO: grouping by date

        return dailyUsagesForCourse;
    }
}
