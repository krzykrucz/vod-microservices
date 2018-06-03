package com.krzykrucz.payment.application.payment;

import com.google.common.collect.ImmutableMap;
import com.krzykrucz.payment.domain.movie.MovieRequestPayload;
import com.krzykrucz.payment.domain.movie.MovieRequestProperty;
import lombok.Getter;
import lombok.Setter;

import static com.krzykrucz.payment.domain.payment.paypal.PaypalMovieRequestProperty.PAYMENT_CANCEL_VIEW_URL;
import static com.krzykrucz.payment.domain.payment.paypal.PaypalMovieRequestProperty.PAYMENT_SUCCESS_VIEW_URL;

@Setter
class PurchaseMovieWithPaypalCommand {

    @Getter
    private String movieTitle;

    private String successViewUrl;

    private String cancelViewUrl;

    MovieRequestPayload getPayload() {
        return new MovieRequestPayload(ImmutableMap.<MovieRequestProperty, String>builder()
                .put(PAYMENT_SUCCESS_VIEW_URL, successViewUrl)
                .put(PAYMENT_CANCEL_VIEW_URL, cancelViewUrl)
                .build()
        );
    }

}
