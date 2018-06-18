package com.krzykrucz.movies.application;

import com.krzykrucz.movies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Mono<VideoContent> getVideoContent(@PathVariable("title") String title, @RequestParam("viewer") String viewerName) {
        return Mono.justOrEmpty(streamService.streamVideo(title, new ViewerName(viewerName)));
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
