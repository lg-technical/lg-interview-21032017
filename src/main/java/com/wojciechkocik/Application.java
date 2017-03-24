package com.wojciechkocik;

import com.wojciechkocik.usage.dto.CourseUsageCreate;
import com.wojciechkocik.usage.service.CourseUsageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Random;

@Slf4j
@SpringBootApplication
public class Application {

    private static int DATA_ENTITIES_QUANTITY = 0;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Generates db init values for API testing purposes
    @Bean
    public CommandLineRunner generateDbInitValues(CourseUsageService courseUsageService) {
        return (evt) ->
        {
            long start = Instant.now().toEpochMilli();

            for (int i = 0; i < DATA_ENTITIES_QUANTITY; i++) {
                CourseUsageCreate courseUsageCreate = CourseUsageCreate.builder()
                        .courseId(String.valueOf(new Random().nextInt(10)))
                        .started(
                                ZonedDateTime.now()
                                        .minusDays(new Random().nextInt(50))
                                        .minusHours(new Random().nextInt(23))
                                        .minusMinutes(new Random().nextInt(59))
                        )
                        .timeSpent(new Random().nextInt(50000))
                        .userId(String.valueOf(new Random().nextInt(10)))
                        .build();
                courseUsageService.createNewCourseUsage(courseUsageCreate);
            }

            long duration = Instant.now().toEpochMilli() - start;

            log.info(
                    String.format("Generated %d CourseUsage entities with %d milliseconds",
                            DATA_ENTITIES_QUANTITY, duration)
            );
        };
    }
}
