package com.wojciechkocik.usage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Data
@Builder
public class CourseUsageCreate {

    @Tolerate
    public CourseUsageCreate() { }

    @NotNull
    private String userId;

    @NotNull
    private String courseId;

    //eg. 2017-12-03T10:15:30+01:00[Europe/Warsaw]
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime started;

    @Min(0)
    private long timeSpent;
}
