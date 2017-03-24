package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.dto.DailyUsageResponse;
import com.wojciechkocik.usage.entity.CourseUsage;
import com.wojciechkocik.usage.repository.CourseUsageRepository;
import org.jfairy.Fairy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseUsageServiceTest {

    private Fairy fairy = Fairy.create();

    @Autowired
    private CourseUsageService courseUsageService;

    @Autowired
    private CourseUsageRepository courseUsageRepository;

    @Before
    public void setUp() {
        courseUsageRepository.deleteAll();
    }

    @Test
    public void createNewCourseUsage_dtoIsProperMappedToDatabase() {

        //Arrange
        String courseId = fairy.textProducer().randomString(10);
        String userId = fairy.textProducer().randomString(10);
        long timeSpent = 5000;
        ZonedDateTime started = ZonedDateTime.now();

        CourseUsageCreate courseUsageCreate = new CourseUsageCreate();
        courseUsageCreate.setCourseId(courseId);
        courseUsageCreate.setUserId(userId);
        courseUsageCreate.setTimeSpent(timeSpent);
        courseUsageCreate.setStarted(started);

        CourseUsage courseUsageExpected = new CourseUsage();
        courseUsageExpected.setTimeSpent(timeSpent);
        courseUsageExpected.setUserId(userId);
        courseUsageExpected.setCourseId(courseId);
        courseUsageExpected.setStarted(started);

        //Act
        CourseUsage courseUsageActual = courseUsageService.createNewCourseUsage(courseUsageCreate);
        courseUsageExpected.setId(courseUsageActual.getId());

        //Arrange
        Assert.assertEquals(courseUsageExpected, courseUsageActual);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createNewCourseUsage_whenArgFieldsAreNull_throwsConstraintViolationException() {
        //Arrange
        CourseUsageCreate courseUsageCreate = new CourseUsageCreate();

        //Act
        CourseUsage courseUsageActual = courseUsageService.createNewCourseUsage(courseUsageCreate);
    }

    @Test
    public void findDailyUsageForCourse_whenCourseSessionCrossedMidnight_thenRestTimePassedToNextDay() {
        //Arrange
        int timeSpentMinutes = 5;
        String courseId = fairy.textProducer().randomString(10);
        CourseUsage courseUsageWithTimeCrossedMidnight = new CourseUsage();
        ZonedDateTime startedOneMinuteBeforeMidnight = ZonedDateTime.parse("2017-03-23T23:59:00.000+01:00[Europe/Warsaw]");
        String firstSimpleDay = "2017-03-23";
        String secondSimpleDay = "2017-03-24";
        courseUsageWithTimeCrossedMidnight.setStarted(startedOneMinuteBeforeMidnight);
        courseUsageWithTimeCrossedMidnight.setCourseId(courseId);
        courseUsageWithTimeCrossedMidnight.setUserId(fairy.textProducer().randomString(10));
        courseUsageWithTimeCrossedMidnight.setTimeSpent(Duration.ofMinutes(timeSpentMinutes).getSeconds()); //one minute in the next day

        courseUsageRepository.save(courseUsageWithTimeCrossedMidnight);

        int responseSizeExpected = 2;

        long firstDayMinutesExpected = 1;
        long secondDayMinutesExpected = timeSpentMinutes - firstDayMinutesExpected;

        //Act
        List<DailyUsageResponse> dailyUsagesForCourse = courseUsageService.findDailyUsageForCourse(courseId);
        int responseSizeActual = dailyUsagesForCourse.size();
        long firstDayMinutesActual = dailyUsagesForCourse.stream().filter(f->f.getDateTime().equals(firstSimpleDay))
                .collect(Collectors.toList()).get(0).getTime();
        long secondDayMinutesActual = dailyUsagesForCourse.stream().filter(f->f.getDateTime().equals(secondSimpleDay))
                .collect(Collectors.toList()).get(0).getTime();

        //convert to minutes
        firstDayMinutesActual = Duration.ofSeconds(firstDayMinutesActual).toMinutes();
        secondDayMinutesActual = Duration.ofSeconds(secondDayMinutesActual).toMinutes();

        //Assert
        Assert.assertEquals(responseSizeExpected, responseSizeActual);
        Assert.assertEquals(firstDayMinutesExpected, firstDayMinutesActual);
        Assert.assertEquals(secondDayMinutesExpected, secondDayMinutesActual);
    }

    @Test
    public void findDailyUsageForCourse_groupDate_hasProperSpentTime() {
        //Arrange
        ZonedDateTime started = ZonedDateTime.parse("2017-03-24T11:56:26.595+01:00[Europe/Belgrade]");
        int entitiesWithSameDateForGroup = 5;
        String userId = fairy.textProducer().randomString(10);
        String courseId = fairy.textProducer().randomString(10);

        for (int i = 0; i < entitiesWithSameDateForGroup; i++) {
            CourseUsage courseUsage = new CourseUsage(
                    started,
                    new Random().nextInt(5000),
                    userId,
                    courseId
            );
            courseUsageRepository.save(courseUsage);
        }

        int sizeExpected = 1;

        //Act
        List<DailyUsageResponse> dailyUsageForCourse = courseUsageService.findDailyUsageForCourse(courseId);
        int sizeActual = dailyUsageForCourse.size();

        //Assert
        Assert.assertEquals(sizeExpected, sizeActual);
    }

    @Test
    public void findDailyUsageForCourse_returnsProperDateFormat() {
        //Arrange
        ZonedDateTime dateTime = ZonedDateTime.now();
        String userId = fairy.textProducer().randomString(10);
        String courseId = fairy.textProducer().randomString(10);

        CourseUsage courseUsage = new CourseUsage(
                dateTime,
                new Random().nextInt(50000),
                userId,
                courseId
        );

        courseUsageRepository.save(courseUsage);

        String dateExpected = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);

        //Act
        List<DailyUsageResponse> dailyUsageForCourse = courseUsageService.findDailyUsageForCourse(courseId);


        //Assert
    }

    @Test
    public void findDailyUsageForCourse_whenDaysWithoutActivity_notPresentInResponse() {
        //Arrange

        //Act

        //Assert
    }

    @After
    public void tearDown() {
        courseUsageRepository.deleteAll();
    }

}
