package com.krzykrucz.payment.application.payment;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerProvider;
import com.krzykrucz.payment.domain.customer.CustomerRepository;
import com.krzykrucz.payment.domain.movie.Movie;
import com.krzykrucz.payment.domain.movie.MovieRequestPayload;
import com.krzykrucz.payment.domain.payment.PaymentView;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
class PaymentApplicationService {

    private final CustomerProvider customerProvider;

    private final CustomerRepository customerRepository;

    private final MovieFinder movieFinder;

    public PaymentApplicationService(CustomerProvider customerProvider, CustomerRepository customerRepository, MovieFinder movieFinder) {
        this.customerProvider = customerProvider;
        this.customerRepository = customerRepository;
        this.movieFinder = movieFinder;
    }

    Try<PaymentView> buyMovie(PurchaseMovieWithPaypalCommand command) {
        final Movie movieToPurchase = movieFinder.findMovieByTitle(command.getMovieTitle());
        final MovieRequestPayload movieRequestPayload = command.getPayload();
        final Customer currentCustomer = customerProvider.getCustomerByName(command.getCurrentCustomerName());
        return currentCustomer
                .requestMovie(movieToPurchase, movieRequestPayload)
                .onSuccess($ -> customerRepository.save(currentCustomer));
    }

    Try<Void> executePayment(ExecutePaymentCommand command) {
        final Customer currentCustomer = customerProvider
                .getCustomerByName(command.getCustomerName());
        return currentCustomer
                .confirmMoviePayment(command.getPaymentId(), command.getPayerId())
                .onSuccess($ -> customerRepository.save(currentCustomer));
    }

    void cancelPayment(CancelPaymentCommand command) {
        final Customer currentCustomer = customerProvider.getCustomerByName(command.getCurrentCustomerName());
        currentCustomer.cancelMoviePayment(command.getPaymentId());
        customerRepository.save(currentCustomer);
    }

}
