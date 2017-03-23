package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.entity.CourseUsage;
import com.wojciechkocik.usage.repository.CourseUsageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
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
}
