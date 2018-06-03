package com.krzykrucz.payment.application.payment;

import com.krzykrucz.payment.domain.movie.Movie;

public interface MovieFinder {
    Movie findMovieByTitle(String movieTitle);
}
