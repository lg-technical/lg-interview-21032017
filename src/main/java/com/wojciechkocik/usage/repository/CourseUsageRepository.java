package com.wojciechkocik.usage.repository;

import com.wojciechkocik.usage.entity.CourseUsage;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface CourseUsageRepository extends CrudRepository<CourseUsage, Long> {
}
