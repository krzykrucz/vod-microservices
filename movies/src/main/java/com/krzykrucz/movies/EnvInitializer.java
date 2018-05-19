package com.krzykrucz.movies;

import com.krzykrucz.movies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.File;
import java.util.UUID;

public class EnvInitializer implements ApplicationRunner {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final File videoFile = new File("sample.mp4");
        videoRepository.save(new VideoInfo(
                new VideoId(UUID.randomUUID()),
                "Harry Potter",
                VideoContent.fromFile(videoFile),
                Price.fromUSD(10)
        ));
    }
}
