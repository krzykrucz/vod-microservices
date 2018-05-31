package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.application.MovieFinder;
import com.krzykrucz.payment.domain.Movie;
import org.springframework.stereotype.Service;

@Service
class MovieFinderImpl implements MovieFinder {
    @Override
    public Movie findMovieByTitle(String movieTitle) {
        return null;
    }
}
