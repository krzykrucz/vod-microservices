package com.krzykrucz.payment.infrastructure.movie;

import com.krzykrucz.payment.application.payment.MovieFinder;
import com.krzykrucz.payment.domain.movie.Movie;
import com.krzykrucz.payment.domain.movie.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MovieFinderImpl implements MovieFinder {

    private final VideosClient videosClient;

    @Autowired
    MovieFinderImpl(VideosClient videosClient) {
        this.videosClient = videosClient;
    }

    @Override
    public Movie findMovieByTitle(String movieTitle) {
        final VideoInfoDTO videoInfo = videosClient.getVideoInfo(movieTitle);
        final String title = videoInfo.getTitle();
        final Price price = new Price(videoInfo.getPrice());
        return new Movie(price, title);
    }
}
