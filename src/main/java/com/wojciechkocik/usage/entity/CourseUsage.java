package com.wojciechkocik.usage.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Data
@Entity
public class CourseUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date started;

    private long timeSpent;

    private String userId;

    private String courseId;
}
