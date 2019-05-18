package com.krzykrucz.movies.domain;

import java.util.Set;

public class Viewer {

    private final Set<VideoId> boughtVideos;

    public Viewer(Set<VideoId> boughtVideos) {
        this.boughtVideos = boughtVideos;
    }

    public boolean hasBoughtVideo(VideoDetails videoDetails) {
        return videoDetails.getPrice().isZero() || boughtVideos.contains(videoDetails.getVideoId());
    }
}
