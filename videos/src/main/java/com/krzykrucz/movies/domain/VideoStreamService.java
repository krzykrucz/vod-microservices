package com.krzykrucz.movies.domain;

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

    public Optional<VideoContent> streamVideo(String videoTitle) {
        final Optional<VideoInfo> videoInfo = videoRepository.findVideoInfoByTitle(videoTitle);
        if (!videoInfo.isPresent()) {
            return Optional.empty();
        }
        final boolean canViewerStreamVideo = viewerProvider.getCurrentViewer()
                .hasBoughtVideo(videoInfo.get().getVideoId());
        if (!canViewerStreamVideo) {
            return Optional.empty();
        }
        return videoRepository.findVideoContentByTitle(videoTitle);
    }

}
