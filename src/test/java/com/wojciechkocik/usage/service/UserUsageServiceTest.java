package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsageForUser;
import com.wojciechkocik.usage.dto.PerCourseUsageForUser;
import com.wojciechkocik.usage.entity.CourseUsage;
import com.wojciechkocik.usage.repository.CourseUsageRepository;
import org.jfairy.Fairy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author Wojciech Kocik
 * @since 23.03.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUsageServiceTest {

    @Autowired
    private CourseUsageRepository courseUsageRepository;

    @Autowired
    private UserUsageService userUsageService;

    @Autowired
    CommandLineRunner commandLineRunner;

    Fairy fairy = Fairy.create();

    @Before
    public void setUp() {
        courseUsageRepository.deleteAll();
    }

    @Test
    public void findPerCourseUsage_groupCourse_hasProperSpentTime() throws Exception {
        //Arrange
        int coursesQuantity = 10;
        String courseId = fairy.textProducer().randomString(50);
        String userId = fairy.textProducer().randomString(50);

        long timeSpentSumExpected = 0;

        for (int i = 0; i < coursesQuantity; i++) {
            int spentTime = new Random().nextInt(50000);
            timeSpentSumExpected += spentTime;
            CourseUsage courseUsage = new CourseUsage();
            courseUsage.setStarted(ZonedDateTime.now());
            courseUsage.setUserId(userId);
            courseUsage.setCourseId(courseId);
            courseUsage.setTimeSpent(spentTime);
            courseUsageRepository.save(courseUsage);
        }

        //Act
        List<PerCourseUsageForUser> perCourseUsage = userUsageService.findPerCourseUsage(userId);
        long timeSpentSumActual = perCourseUsage.get(0).getTime();

        //Assert
        Assert.assertEquals(timeSpentSumExpected, timeSpentSumActual);
    }

    @Test
    public void findPerCourseUsage_whenCoursesWithoutActivity_notPresentInResponse() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void findDailyUsagesForUser_whenCourseSessionCrossedMidnight_thenRestTimePassedToNextDay() {
        //Arrange
        String userId = fairy.textProducer().randomString(10);
        CourseUsage courseUsageWithTimeCrossedMidnight = new CourseUsage();
        ZonedDateTime startedOneMinuteBeforeMidnight = ZonedDateTime.parse("2017-03-23T23:59:00.000+01:00[Europe/Warsaw]");
        courseUsageWithTimeCrossedMidnight.setStarted(startedOneMinuteBeforeMidnight);
        courseUsageWithTimeCrossedMidnight.setCourseId(fairy.textProducer().randomString(10));
        courseUsageWithTimeCrossedMidnight.setUserId(userId);
        courseUsageWithTimeCrossedMidnight.setTimeSpent(Duration.ofMinutes(2).getSeconds()); //one minute in the next day

        courseUsageRepository.save(courseUsageWithTimeCrossedMidnight);

        int responseSizeExpected = 2;

        //Act
        List<DailyUsageForUser> dailyUsagesForCourse = userUsageService.findDailyUsagesForUser(userId);
        int responseSizeActual = dailyUsagesForCourse.size();

        //Assert
        Assert.assertEquals(responseSizeExpected, responseSizeActual);
    }

    @Test
    public void findDailyUsagesForUser_returnsProperDateFormat() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void findDailyUsagesForUser_whenDaysWithoutActivity_notPresentInResponse() {
        //Arrange

        //Act

        //Assert
    }

    @After
    public void tearDown() {
        courseUsageRepository.deleteAll();
    }


}
