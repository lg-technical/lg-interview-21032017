package com.wojciechkocik.usage.controller;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.entity.CourseUsage;
import com.wojciechkocik.usage.service.CourseUsageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Handles /usage/course/** http requests
 *
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Slf4j
@RestController
@RequestMapping("/usage/course")
public class CourseUsageController {

    private final CourseUsageService courseUsageService;

    public CourseUsageController(CourseUsageService courseUsageService) {
        this.courseUsageService = courseUsageService;
    }

    @PostMapping
    public CourseUsage create(@Valid @RequestBody CourseUsageCreate courseUsageCreate) {
        log.info("Creating new course usage entity");
        return courseUsageService.createNewCourseUsage(courseUsageCreate);
    }

    @GetMapping("/{courseId}/daily")
    public List<DailyUsageResponse> getDailyUsage(@PathVariable String courseId) {
        return courseUsageService.findDailyUsageForCourse(courseId);
    }
}
