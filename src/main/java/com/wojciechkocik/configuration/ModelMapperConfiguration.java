package com.wojciechkocik.configuration;

import com.wojciechkocik.usage.converter.CourseUsageCreateToCourseUsageEntityConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wojciech Kocik
 * @since 22.03.2017
 */
@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true);

        modelMapper.addConverter(new CourseUsageCreateToCourseUsageEntityConverter());

        return modelMapper;
    }
}
