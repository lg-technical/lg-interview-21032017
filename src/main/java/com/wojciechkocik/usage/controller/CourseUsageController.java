package com.wojciechkocik.usage.controller;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.service.CourseUsageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Slf4j
@RestController("/usage/course")
public class CourseUsageController {

    private final CourseUsageService courseUsageService;

    public CourseUsageController(CourseUsageService courseUsageService) {
        this.courseUsageService = courseUsageService;
    }

    @PostMapping
    public void create(CourseUsageCreate courseUsageCreate) {
        //TODO: service for storing data
        log.info(courseUsageCreate.toString());
    }

}
