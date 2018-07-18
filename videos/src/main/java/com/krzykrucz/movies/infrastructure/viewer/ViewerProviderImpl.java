package com.krzykrucz.movies.infrastructure.viewer;

import com.krzykrucz.movies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ViewerProviderImpl implements ViewerProvider {

    private final CustomerClient customerClient;

    private final VideoRepository videoRepository;

    @Autowired
    public ViewerProviderImpl(CustomerClient customerClient, VideoRepository videoRepository) {
        this.customerClient = customerClient;
        this.videoRepository = videoRepository;
    }

    @Override
    public Viewer getCurrentViewer(ViewerName viewerName) {
        final CustomerDTO currentCustomer = customerClient.getCurrentCustomer(viewerName.getName());
        final Set<VideoId> videosIds = currentCustomer.getPurchasedMovies().stream()
                .map(this::getIdForMovieDTO)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        return new Viewer(videosIds);
    }

    private Optional<VideoId> getIdForMovieDTO(MovieDTO movie) {
        return videoRepository.findVideoInfoByTitle(movie.getTitle())
                .map(VideoInfo::getVideoId);
    }

}
