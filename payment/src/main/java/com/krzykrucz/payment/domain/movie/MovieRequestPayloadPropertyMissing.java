package com.krzykrucz.payment.domain.movie;

import com.krzykrucz.payment.domain.payment.paypal.PaypalMovieRequestProperty;

public class MovieRequestPayloadPropertyMissing extends Exception {
    public MovieRequestPayloadPropertyMissing(PaypalMovieRequestProperty property) {
        super("Missing property in movie request: " + property.name());
    }
}
