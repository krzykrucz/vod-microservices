package com.krzykrucz.movies.domain;

import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoStreamService {

    private final ViewerProvider viewerProvider;

    private final VideoRepository videoRepository;

    public VideoStreamService(ViewerProvider viewerProvider, VideoRepository videoRepository) {
        this.viewerProvider = viewerProvider;
        this.videoRepository = videoRepository;
    }

    public Either<VideoStreamFailure, VideoContent> streamVideo(String videoTitle, ViewerName viewerName) {
        final Optional<VideoDetails> videoInfo = videoRepository.findVideoInfoByTitle(videoTitle);
        if (!videoInfo.isPresent()) {
            return Either.left(VideoStreamFailure.VIDEO_NOT_EXISTS);
        }
        final boolean canViewerStreamVideo = viewerProvider.getCurrentViewer(viewerName)
                .hasBoughtVideo(videoInfo.get());
        if (!canViewerStreamVideo) {
            return Either.left(VideoStreamFailure.VIDEO_NOT_BOUGHT);
        }
        return videoRepository.findVideoContentByTitle(videoTitle)
                .map(Either::<VideoStreamFailure, VideoContent>right)
                .orElseGet(() -> Either.left(VideoStreamFailure.VIDEO_NOT_EXISTS));
    }

}
