package com.krzykrucz.payment.application.payment;

import com.krzykrucz.payment.domain.customer.CurrentCustomerProvider;
import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerRepository;
import com.krzykrucz.payment.domain.movie.Movie;
import com.krzykrucz.payment.domain.movie.MovieRequestPayload;
import com.krzykrucz.payment.domain.payment.PayerId;
import com.krzykrucz.payment.domain.payment.PaymentId;
import com.krzykrucz.payment.domain.payment.PaymentView;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
class PaymentApplicationService {

    private final CurrentCustomerProvider currentCustomerProvider;

    private final CustomerRepository customerRepository;

    private final MovieFinder movieFinder;

    public PaymentApplicationService(CurrentCustomerProvider currentCustomerProvider, CustomerRepository customerRepository, MovieFinder movieFinder) {
        this.currentCustomerProvider = currentCustomerProvider;
        this.customerRepository = customerRepository;
        this.movieFinder = movieFinder;
    }

    Try<PaymentView> buyMovie(@RequestBody PurchaseMovieWithPaypalCommand command) {
        final Movie movieToPurchase = movieFinder.findMovieByTitle(command.getMovieTitle());
        final MovieRequestPayload movieRequestPayload = command.getPayload();
        final Customer currentCustomer = currentCustomerProvider.getCurrentCustomer();
        return currentCustomer
                .requestMovie(movieToPurchase, movieRequestPayload)
                .onSuccess($ -> customerRepository.save(currentCustomer));
    }

    Try<Void> executePayment(String paymentId, String payerId) {
        final Customer currentCustomer = currentCustomerProvider.getCurrentCustomer();
        return currentCustomer
                .confirmMoviePayment(new PaymentId(paymentId), new PayerId(payerId))
                .onSuccess($ -> customerRepository.save(currentCustomer));
    }

    void cancelPayment(String paymentId) {
        final Customer currentCustomer = currentCustomerProvider.getCurrentCustomer();
        currentCustomer
                .cancelMoviePayment(new PaymentId(paymentId));
        customerRepository.save(currentCustomer);
    }

}
