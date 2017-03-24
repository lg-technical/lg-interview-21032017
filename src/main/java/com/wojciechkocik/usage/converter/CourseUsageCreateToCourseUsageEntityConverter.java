package com.wojciechkocik.usage.converter;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.entity.CourseUsage;
import org.modelmapper.AbstractConverter;

/**
 * Converts dto {@link CourseUsageCreate} to db entity {@link CourseUsage}
 *
 * @author Wojciech Kocik
 * @since 23.03.2017
 */
public class CourseUsageCreateToCourseUsageEntityConverter
        extends AbstractConverter<CourseUsageCreate, CourseUsage> {
    @Override
    protected CourseUsage convert(CourseUsageCreate source) {
        CourseUsage courseUsage = new CourseUsage();

        courseUsage.setCourseId(source.getCourseId());
        courseUsage.setStarted(source.getStarted());
        courseUsage.setTimeSpent(source.getTimeSpent());
        courseUsage.setUserId(source.getUserId());
        return courseUsage;
    }
}
