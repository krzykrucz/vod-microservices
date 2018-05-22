package com.krzykrucz.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = {
                        ".*JpaConfig.*",
                })
})
@EnableFeignClients
public class VideosApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideosApplication.class, args);
    }

}
