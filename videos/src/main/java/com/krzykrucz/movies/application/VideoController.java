package com.krzykrucz.movies.application;

import com.krzykrucz.movies.domain.VideoContent;
import com.krzykrucz.movies.domain.VideoInfo;
import com.krzykrucz.movies.domain.VideoRepository;
import com.krzykrucz.movies.domain.VideoStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoRepository videoRepository;

    private final VideoStreamService streamService;

    @Autowired
    public VideoController(VideoRepository videoRepository, VideoStreamService streamService) {
        this.videoRepository = videoRepository;
        this.streamService = streamService;
    }

    @GetMapping("/content/{title}")
    public Mono<VideoContent> getVideoContent(@PathVariable("title") String title) {
        return Mono.justOrEmpty(streamService.streamVideo(title));
    }

    @GetMapping("/all")
    public Flux<VideoInfoDTO> getAllVideoInfos() {
        final Iterable<VideoInfo> allInfos = videoRepository.findAllInfos();
        return Flux.fromIterable(videoRepository.findAllInfos())
                .map(VideoInfoDTO::fromVideoInfo);
    }

    @GetMapping("/{title}")
    public Mono<VideoInfoDTO> getVideoInfo(@PathVariable("title") String title) {
        final Optional<VideoInfoDTO> videoInfoDTO = videoRepository.findVideoInfoByTitle(title)
                .map(VideoInfoDTO::fromVideoInfo);
        return Mono.justOrEmpty(videoInfoDTO);
    }

}
