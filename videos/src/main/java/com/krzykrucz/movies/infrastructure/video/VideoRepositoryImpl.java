package com.krzykrucz.movies.infrastructure.video;

import com.krzykrucz.movies.domain.VideoContent;
import com.krzykrucz.movies.domain.VideoId;
import com.krzykrucz.movies.domain.VideoInfo;
import com.krzykrucz.movies.domain.VideoRepository;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
class VideoRepositoryImpl implements VideoRepository {

    private final VideoContentStore videoContentStore;

    private final PersistentVideoMongoRepository persistentVideoMongoRepository;

    public VideoRepositoryImpl(VideoContentStore videoContentStore, PersistentVideoMongoRepository persistentVideoMongoRepository) {
        this.videoContentStore = videoContentStore;
        this.persistentVideoMongoRepository = persistentVideoMongoRepository;
    }

    @Override
    public void save(VideoInfo videoInfo, VideoContent content) {
        final PersistentVideoInfo persistentVideoInfo = new PersistentVideoInfo(
                videoInfo.getVideoId().getUuid(),
                videoInfo.getTitle(),
                videoInfo.getPrice());
        try (final InputStream contentStream = content.toStream()) {
            videoContentStore.setContent(persistentVideoInfo, contentStream);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save videoInfo of title: " + videoInfo.getTitle());
        }
        persistentVideoMongoRepository.save(persistentVideoInfo);
    }

    @Override
    public Optional<VideoInfo> findVideoInfoByTitle(String title) {
        return persistentVideoMongoRepository.findByTitle(title)
                .map(this::toVideoInfo);
    }

    @Override
    public Optional<VideoContent> findVideoContentByTitle(String title) {
        return persistentVideoMongoRepository.findByTitle(title)
                .map(this::tryFindingContent)
                .filter(Try::isSuccess)
                .map(Try::get);
    }

    @Override
    public Iterable<VideoInfo> findAllInfos() {
        return persistentVideoMongoRepository.findAll().stream()
                .map(this::toVideoInfo)
                .collect(Collectors.toList());
    }

    private VideoInfo toVideoInfo(PersistentVideoInfo persistentVideoInfo) {
        return new VideoInfo(
                new VideoId(persistentVideoInfo.getVideoId()),
                persistentVideoInfo.getTitle(),
                persistentVideoInfo.getPrice());
    }

    private Try<VideoContent> tryFindingContent(PersistentVideoInfo videoInfo) {
        final InputStream streamContentFromStore = videoContentStore.getContent(videoInfo);
        return Try.of(() -> VideoContent.fromStream(streamContentFromStore));
    }
}