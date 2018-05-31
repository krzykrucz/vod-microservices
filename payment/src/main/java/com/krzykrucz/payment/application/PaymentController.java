package com.krzykrucz.payment.application;

import com.krzykrucz.payment.domain.Movie;
import com.krzykrucz.payment.domain.customer.CurrentCustomerProvider;
import com.krzykrucz.payment.domain.payment.PaymentId;
import com.krzykrucz.payment.domain.payment.PaymentView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/payment")
public class PaymentController {

    private final CurrentCustomerProvider currentCustomerProvider;

    private final MovieFinder movieFinder;

    public PaymentController(CurrentCustomerProvider currentCustomerProvider, MovieFinder movieFinder) {
        this.currentCustomerProvider = currentCustomerProvider;
        this.movieFinder = movieFinder;
    }

    @PostMapping("/buy")
    public PaymentView buyMovie(@RequestBody PurchaseMovieCommand command) {
        final Movie movieToPurchase = movieFinder.findMovieByTitle(command.getMovieTitle());
        return currentCustomerProvider.getCurrentCustomer()
                .requestMovie(movieToPurchase);
    }

    @PostMapping("/success")
    public void executePaymentCommand(@RequestParam("paymentId") String paymentId) {
        currentCustomerProvider.getCurrentCustomer()
                .confirmMoviePayment(new PaymentId(paymentId));
    }

    @PostMapping("/cancel")
    public void cancelPayment(@RequestParam("paymentId") String paymentId) {
        currentCustomerProvider.getCurrentCustomer()
                .cancelMoviePayment(new PaymentId(paymentId));
    }
}
