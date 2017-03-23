package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsageForUser;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface UserUsageService {

    List<DailyUsageForUser> findDailyUsagesForUser(String userId);

    List<PerCourseUsageForUser> findPerCourseUsage(String userId);
}
