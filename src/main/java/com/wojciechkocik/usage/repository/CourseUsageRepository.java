package com.wojciechkocik.usage.repository;

import com.wojciechkocik.usage.dto.DailyUsage;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;
import com.wojciechkocik.usage.entity.CourseUsage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
public interface CourseUsageRepository extends CrudRepository<CourseUsage, Long> {

    @Query(value = "select new com.wojciechkocik.usage.dto.DailyUsage(v.started, v.timeSpent) " +
            "from CourseUsage v " +
            "where v.userId = ?1 and v.timeSpent > 0")
    List<DailyUsage> findSpentTimeByUserIdOnDate(String userId);

    @Query(value = "select new com.wojciechkocik.usage.dto.PerCourseUsageForUser(v.courseId, sum(v.timeSpent)) " +
            "from CourseUsage v " +
            "where v.userId = ?1 and v.timeSpent > 0" +
            "group by v.courseId")
    List<PerCourseUsageForUser> findPerCourseSpentTimeByUserId(String userId);

    @Query(value = "select new com.wojciechkocik.usage.dto.DailyUsage(v.started, v.timeSpent) " +
            "from CourseUsage v " +
            "where v.courseId = ?1 and v.timeSpent > 0")
    List<DailyUsage> findSpentTimeByCourseIdOnDate(String courseId);
}
