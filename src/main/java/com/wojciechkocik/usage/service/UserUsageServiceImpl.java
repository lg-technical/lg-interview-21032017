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

    @Override
    public List<DailyUsageResponse> findDailyUsagesForUser(String userId) {
        List<DailyUsage> dailyUsagesForUser = courseUsageRepository.findSpentTimeByUserIdOnDate(userId);
        List<DailyUsage> processedListByCrossedMidnightMechanism = crossMidnightService.run(dailyUsagesForUser);
        dailyUsagesForUser.addAll(processedListByCrossedMidnightMechanism);
        return DailyUsageUtils.groupByDateAndSumTime(dailyUsagesForUser);
    }

    @Override
    public List<PerCourseUsageForUser> findPerCourseUsage(String userId) {
        return courseUsageRepository.findPerCourseSpentTimeByUserId(userId);
    }
}
