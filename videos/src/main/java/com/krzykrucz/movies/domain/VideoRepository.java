package com.krzykrucz.movies.domain;

import java.util.List;
import java.util.Optional;

public interface VideoRepository {
    void save(VideoDetails videoDetails, VideoContent content);

    Optional<VideoDetails> findVideoInfoByTitle(String title);

    Optional<VideoContent> findVideoContentByTitle(String title);

    List<VideoDetails> findAllInfos();

}
