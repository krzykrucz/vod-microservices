package com.krzykrucz.movies.infrastructure.video;

import com.krzykrucz.movies.domain.VideoContent;
import com.krzykrucz.movies.domain.VideoDetails;
import com.krzykrucz.movies.domain.VideoId;
import com.krzykrucz.movies.domain.VideoRepository;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
    public void save(VideoDetails videoDetails, VideoContent content) {
        final PersistentVideoInfo persistentVideoInfo = new PersistentVideoInfo(
                videoDetails.getVideoId().getUuid(),
                videoDetails.getTitle(),
                videoDetails.getPrice());
        try (final InputStream contentStream = content.toStream()) {
            videoContentStore.setContent(persistentVideoInfo, contentStream);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save videoDetails of title: " + videoDetails.getTitle());
        }
        persistentVideoMongoRepository.save(persistentVideoInfo);
    }

    @Override
    public Optional<VideoDetails> findVideoInfoByTitle(String title) {
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
    public List<VideoDetails> findAllInfos() {
        return persistentVideoMongoRepository.findAll().stream()
                .map(this::toVideoInfo)
                .collect(Collectors.toList());
    }

    private VideoDetails toVideoInfo(PersistentVideoInfo persistentVideoInfo) {
        return new VideoDetails(
                new VideoId(persistentVideoInfo.getVideoId()),
                persistentVideoInfo.getTitle(),
                persistentVideoInfo.getPrice());
    }

    private Try<VideoContent> tryFindingContent(PersistentVideoInfo videoInfo) {
        final InputStream streamContentFromStore = videoContentStore.getContent(videoInfo);
        return Try.of(() -> VideoContent.fromStream(streamContentFromStore));
    }
}
