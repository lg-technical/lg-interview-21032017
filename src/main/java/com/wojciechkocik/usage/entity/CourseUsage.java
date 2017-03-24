package com.wojciechkocik.usage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Database entity for persist data about Course Usage Statistics
 *
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Data
@Entity
@NoArgsConstructor
public class CourseUsage {

    public CourseUsage(ZonedDateTime started, long timeSpent, String userId, String courseId) {
        this.started = started;
        this.timeSpent = timeSpent;
        this.userId = userId;
        this.courseId = courseId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private ZonedDateTime started;

    @Min(0)
    private long timeSpent;

    @NotNull
    private String userId;

    @NotNull
    private String courseId;
}
