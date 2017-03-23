package com.wojciechkocik.usage.dto;

import lombok.Data;

/**
 * @author Wojciech Kocik
 * @since 23.03.2017
 */
@Data
public class PerCourseUsageForUser {

    public PerCourseUsageForUser(String courseId, long time) {
        this.courseId = courseId;
        this.time = time;
    }

    private String courseId;
    private long time;
}
