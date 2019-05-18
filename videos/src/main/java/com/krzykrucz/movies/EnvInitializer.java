package com.krzykrucz.movies;

import com.krzykrucz.movies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Profile("!test")
@Component
public class EnvInitializer implements ApplicationRunner {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final File videoFile = new File(getClass()
                .getClassLoader()
                .getResource("videos/sample.mp4")
                .getFile());
        final VideoDetails harryPotterInfo = new VideoDetails(
                new VideoId(UUID.fromString("be6d38e8-d5bd-4929-9c0c-56879f91a172")),
                "Harry Potter",
                Price.fromUSD(10)
        );
        final VideoDetails spidermanInfo = new VideoDetails(
                new VideoId(UUID.randomUUID()),
                "Spiderman",
                Price.fromUSD(0)
        );
        final VideoContent harryPotterContent = VideoContent.fromFile(videoFile);
        if (!videoRepository.findVideoInfoByTitle("Harry Potter").isPresent()) {
            videoRepository.save(harryPotterInfo, harryPotterContent);
        }
        if (!videoRepository.findVideoInfoByTitle("Spiderman").isPresent()) {
            videoRepository.save(spidermanInfo, harryPotterContent);
        }
    }
}
