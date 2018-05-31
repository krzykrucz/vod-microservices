package com.krzykrucz.payment.application;

import com.krzykrucz.payment.domain.Movie;

public interface MovieFinder {
    Movie findMovieByTitle(String movieTitle);
}
