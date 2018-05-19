package com.krzykrucz.movies.application;

import com.krzykrucz.movies.domain.VideoContent;
import com.krzykrucz.movies.domain.VideoInfo;
import com.krzykrucz.movies.domain.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/video")
public class VideoController {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @GetMapping("/content/{title}")
    public Mono<VideoContent> getVideoContent(@PathVariable("title") String title) {
        return Mono.justOrEmpty(videoRepository.findVideoContentByTitle(title));
    }

    @GetMapping("/all")
    public Flux<VideoInfo> getAllVideoInfos() {
        return Flux.fromIterable(videoRepository.findAllInfos());
    }

    @GetMapping("/{title}")
    public Mono<VideoInfo> getAllVideoInfos(@PathVariable("title") String title) {
        return Mono.justOrEmpty(videoRepository.findVideoInfoByTitle(title));
    }

}
