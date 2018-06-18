package com.krzykrucz.movies.domain;

import java.util.List;
import java.util.Optional;

public interface VideoRepository {
    void save(VideoInfo videoInfo, VideoContent content);

    Optional<VideoInfo> findVideoInfoByTitle(String title);

    Optional<VideoContent> findVideoContentByTitle(String title);

    List<VideoInfo> findAllInfos();

}
