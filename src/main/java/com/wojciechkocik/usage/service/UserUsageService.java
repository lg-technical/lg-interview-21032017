package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface UserUsageService {

    List<DailyUsageResponse> findDailyUsagesForUser(String userId);

    List<PerCourseUsageForUser> findPerCourseUsage(String userId);
}
