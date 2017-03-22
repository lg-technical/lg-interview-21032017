package com.wojciechkocik.usage.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Data
public class CourseUsageCreate {
    private String userId;
    private String courseId;
    private Date started;
    private long timeSpent;
}
