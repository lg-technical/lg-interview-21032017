package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;
import com.wojciechkocik.usage.dto.DailyUsageResponse;
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
    private final TimeSpentCrossMidnightService crossMidnightService;

    public UserUsageServiceImpl(CourseUsageRepository courseUsageRepository, TimeSpentCrossMidnightService crossMidnightService) {
        this.courseUsageRepository = courseUsageRepository;
        this.crossMidnightService = crossMidnightService;
    }

    /**
     * Implementation using {@link CourseUsageRepository} to finding data for time user spend on courses
     *
     * @param userId persisted user identity
     *
     * Supports a situation, when the course session crossed the midnight.
     * Groups data by date and summing session spent time.
     *
     * @see UserUsageService
     */
    @Override
    public List<DailyUsageResponse> findDailyUsagesForUser(String userId) {
        List<DailyUsage> dailyUsagesForUser = courseUsageRepository.findSpentTimeByUserIdOnDate(userId);
        List<DailyUsage> processedListByCrossedMidnightMechanism = crossMidnightService.divideDaysWithTimeSessionCrossedMidnight(dailyUsagesForUser);
        return DailyUsageUtils.groupByDateAndSumTime(processedListByCrossedMidnightMechanism);
    }

    /**
     * Implementation using {@link CourseUsageRepository} to finding data for user course usage
     *
     * @param userId persisted user identity
     *
     * @see UserUsageService
     */
    @Override
    public List<PerCourseUsageForUser> findPerCourseUsage(String userId) {
        return courseUsageRepository.findPerCourseSpentTimeByUserId(userId);
    }
}
