package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsageForCourse;
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
    private final ModelMapper modelMapper;

    public CourseUsageServiceImpl(CourseUsageRepository courseUsageRepository, ModelMapper modelMapper) {
        this.courseUsageRepository = courseUsageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseUsage createNewCourseUsage(CourseUsageCreate courseUsageCreate) {
        log.info("Creating new course usage entity");
        CourseUsage courseUsage = modelMapper.map(courseUsageCreate, CourseUsage.class);
        return courseUsageRepository.save(courseUsage);
    }

    @Override
    public List<DailyUsageForCourse> findDailyUsageForCourse(String courseId) {
        return courseUsageRepository.findDailySpentTimeByCourseId(courseId);
    }
}
