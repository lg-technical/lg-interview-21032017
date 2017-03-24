package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;

import java.util.List;

/**
 * Performs business logic for fetch Course Usage data related to userId.
 *
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface UserUsageService {

    /**
     * Getting statistics for time user spend on courses.
     *
     * @param userId persisted user identity
     * @return length of time user spent on any courses every day (in seconds)
     */
    List<DailyUsageResponse> findDailyUsagesForUser(String userId);

    /**
     * Getting statistics for user course usage
     *
     * @param userId persisted user identity
     * @return length of time user spent on each course since beginning of service creation (in seconds)
     */
    List<PerCourseUsageForUser> findPerCourseUsage(String userId);
}
