package com.krzykrucz.movies.domain;

import java.util.Set;

public class Viewer {

    private final Set<VideoId> boughtVideos;

    public Viewer(Set<VideoId> boughtVideos) {
        this.boughtVideos = boughtVideos;
    }

    public boolean hasBoughtVideo(VideoInfo videoInfo) {
        return videoInfo.getPrice().isZero() || boughtVideos.contains(videoInfo.getVideoId());
    }
}
