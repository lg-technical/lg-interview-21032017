package com.wojciechkocik.usage.service;

import com.wojciechkocik.usage.dto.DailyUsage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.util.Pair;

import javax.persistence.Tuple;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Wojciech Kocik
 * @since 28.03.2017
 */
public class TimeSpentCrossMidnightServiceTest {

    private TimeSpentCrossMidnightService timeSpentCrossMidnightService;

    @Before
    public void setUp() throws Exception {
        timeSpentCrossMidnightService = new TimeSpentCrossMidnightServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    private ZonedDateTime getMidnightOfDate(ZonedDateTime dateTime) {
        return dateTime
                .toLocalDate().atTime(LocalTime.MIDNIGHT).atZone(dateTime.getZone());
    }

    @Test
    public void shouldProperlyDivideCrossedMidnightSessionDozenDays() throws Exception {
        //Arrange
        ZonedDateTime timeDateStarted = ZonedDateTime.parse("2017-01-23T23:59:00.000+01:00[Europe/Warsaw]");
        long sessionTime = 60 * 60 * 25 * 10;
        long[] sessionTimeForEveryDay = {60, 86400, 86400, 86400, 86400, 86400, 86400, 86400, 86400, 86400, 86400, 35940};

        //Act & Assert
        shouldProperlyDivideCrossedMidnightSession_parametrized(timeDateStarted, sessionTime, sessionTimeForEveryDay);
    }

    @Test
    public void NoProcessWhenOneDaySession() throws Exception {
        //Arrange
        ZonedDateTime timeDateStarted = ZonedDateTime.parse("2017-03-23T20:59:00.000+01:00[Europe/Warsaw]");
        long sessionTime = 1258;

        List<DailyUsage> dailyUsagesExpected = new ArrayList<>();
        DailyUsage dailyUsage = new DailyUsage(timeDateStarted, sessionTime);
        dailyUsagesExpected.add(dailyUsage);

        //Act & Assert
        List<DailyUsage> dailyUsagesActual = timeSpentCrossMidnightService.divideDaysWithTimeSessionCrossedMidnight(dailyUsagesExpected);

        if (Objects.isNull(dailyUsagesActual) || dailyUsagesActual.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(dailyUsagesExpected.get(0), dailyUsagesActual.get(0));
    }

    @Test
    public void shouldProperlyDivideCrossedMidnightSessionWithChangeTimeInTimezone() throws Exception {
        //in Poland On Saturday night, March 25th, Sunday, March 26, 2017.
        ZonedDateTime timeDateStarted = ZonedDateTime.parse("2017-03-23T23:59:00.000+01:00[Europe/Warsaw]");
        long sessionTime = 60 * 60 * 25 * 5;

        long[] sessionTimeForEveryDay = {60, 86400, 86400, 86400, 86400, 86400, 17940};
        shouldProperlyDivideCrossedMidnightSession_parametrized(timeDateStarted, sessionTime, sessionTimeForEveryDay);
    }

    @Test
    public void shouldReturnEmptyListWhenSessionWithoutPresent() throws Exception {
        //Arrange
        ZonedDateTime timeDateStarted = ZonedDateTime.parse("2015-10-28T20:23:00.000+01:00[Europe/Warsaw]");
        long sessionTime = 0;

        List<DailyUsage> dailyUsages = new ArrayList<>();
        DailyUsage dailyUsage = new DailyUsage(timeDateStarted, sessionTime);
        dailyUsages.add(dailyUsage);

        //Act
        List<DailyUsage> dailyUsagesActual = timeSpentCrossMidnightService.divideDaysWithTimeSessionCrossedMidnight(dailyUsages);

        //Assert
        boolean empty = dailyUsagesActual.isEmpty();
        Assert.assertTrue(empty);
    }

    private void shouldProperlyDivideCrossedMidnightSession_parametrized(ZonedDateTime timeDateStarted, long sessionTime, long[] sessionTimeForEveryDay) {
        //Arrange
        List<DailyUsage> dailyUsages = new ArrayList<>();
        DailyUsage dailyUsage = new DailyUsage(timeDateStarted, sessionTime);
        dailyUsages.add(dailyUsage);

        List<DailyUsage> dailyUsagesExpected = prepareExpectedDailyUsages(timeDateStarted, sessionTimeForEveryDay);

        //Act
        List<DailyUsage> dailyUsagesActual = timeSpentCrossMidnightService.divideDaysWithTimeSessionCrossedMidnight(dailyUsages);

        //Assert
        if (Objects.isNull(dailyUsagesActual) || dailyUsagesActual.size() != sessionTimeForEveryDay.length) {
            Assert.fail();
        }
        //Assert
        for (int i = 0; i < dailyUsagesExpected.size(); i++) {
            Assert.assertEquals(dailyUsagesExpected.get(i), dailyUsagesActual.get(i));
        }
    }

    private List<DailyUsage> prepareExpectedDailyUsages(ZonedDateTime timeDateStarted, long[] sessionTimeForEveryDay) {
        long epochBeforeSession = timeDateStarted.toEpochSecond();

        long untilMidnight = timeDateStarted.until(getMidnightOfDate(timeDateStarted.plusDays(1)), ChronoUnit.SECONDS);
        List<DailyUsage> dailyUsagesExpected = new ArrayList<>();
        long secondsInDay = 60 * 60 * 24;

        for (int i = 0; i < sessionTimeForEveryDay.length; i++) {
            DailyUsage dailyUsageTemp = null;
            if(i == 0){
                dailyUsageTemp = new DailyUsage(timeDateStarted.plusDays(i), sessionTimeForEveryDay[i]);
            }else {
                dailyUsageTemp = new DailyUsage(getMidnightOfDate(timeDateStarted.plusDays(i)), sessionTimeForEveryDay[i]);
            }
            dailyUsagesExpected.add(dailyUsageTemp);
        }
        return dailyUsagesExpected;
    }

    @Test
    public void shouldProperlyDivideCrossedMidnightSessionMoreThanTwoDays() throws Exception {

    }
}