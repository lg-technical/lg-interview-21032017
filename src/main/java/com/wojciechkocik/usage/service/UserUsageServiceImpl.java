package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsageForUser;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;
import com.wojciechkocik.usage.repository.CourseUsageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 23.03.2017
 */
@Service
public class UserUsageServiceImpl implements UserUsageService {

    private final CourseUsageRepository courseUsageRepository;

    public UserUsageServiceImpl(CourseUsageRepository courseUsageRepository) {
        this.courseUsageRepository = courseUsageRepository;
    }

    @Override
    public List<DailyUsageForUser> findDailyUsagesForUser(String userId) {
        return courseUsageRepository.findSpentTimeByUserIdOnDate(userId);
    }

    @Override
    public List<PerCourseUsageForUser> findPerCourseUsage(String userId) {
        return courseUsageRepository.findPerCourseSpentTimeByUserId(userId);
    }
}
