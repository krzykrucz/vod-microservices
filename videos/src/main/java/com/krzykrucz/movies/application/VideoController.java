package com.krzykrucz.movies.application;

import com.krzykrucz.movies.domain.*;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/content/{title}/{viewerName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideoContentResponse> getVideoContent(@PathVariable("title") String title, @PathVariable("viewerName") String viewerName) {
        final Either<VideoStreamFailure, VideoContent> videoContent = streamService.streamVideo(title, new ViewerName(viewerName));
        if (videoContent.isLeft()) {
            final VideoStreamFailure videoStreamFailure = videoContent.getLeft();
            switch (videoStreamFailure) {
                case VIDEO_NOT_BOUGHT:
                    return new ResponseEntity<>(new VideoContentResponse(videoStreamFailure.name()), HttpStatus.FORBIDDEN);
                case VIDEO_NOT_EXISTS:
                    return new ResponseEntity<>(new VideoContentResponse(videoStreamFailure.name()), HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity<>(new VideoContentResponse(videoStreamFailure.name()), HttpStatus.BAD_REQUEST);

            }
        }
        return new ResponseEntity<>(new VideoContentResponse(videoContent.get().getByteContent()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<VideoInfoDTO> getAllVideoInfos() {
        return videoRepository.findAllInfos()
                .stream()
                .map(VideoInfoDTO::fromVideoInfo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{title}")
    public Optional<VideoInfoDTO> getVideoInfo(@PathVariable("title") String title) {
        return videoRepository.findVideoInfoByTitle(title)
                .map(VideoInfoDTO::fromVideoInfo);
    }

}
