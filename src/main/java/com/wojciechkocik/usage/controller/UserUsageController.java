package com.wojciechkocik.usage.controller;

import com.wojciechkocik.usage.dto.DailyUsageForUser;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;
import com.wojciechkocik.usage.service.UserUsageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@RestController
@RequestMapping("/usage/user")
public class UserUsageController {

    private final UserUsageService userUsageService;

    public UserUsageController(UserUsageService userUsageService) {
        this.userUsageService = userUsageService;
    }

    @GetMapping("/{userId}/daily")
    public List<DailyUsageForUser> getDailyUsage(@PathVariable String userId){
        return userUsageService.findDailyUsagesForUser(userId);
    }

    @GetMapping("/{userId}/perCourse")
    public List<PerCourseUsageForUser> getPerCourseUsage(@PathVariable String userId){
        return userUsageService.findPerCourseUsage(userId);
    }

}
