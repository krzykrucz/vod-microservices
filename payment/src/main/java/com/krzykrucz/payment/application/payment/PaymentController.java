package com.krzykrucz.payment.application.payment;

import com.krzykrucz.payment.domain.payment.PaymentView;
import io.vavr.control.Try;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    public PaymentController(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @PostMapping("/buy")
    public PaymentView buyMovieWithPaypal(@RequestBody PurchaseMovieWithPaypalCommand command) {
        final Try<PaymentView> paymentView = paymentApplicationService.buyMovie(command);
        return paymentView.getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
    }

    @PostMapping("/success")
    public void executePaymentCommand(@RequestBody ExecutePaymentCommand command) {
        paymentApplicationService.executePayment(command)
                .getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
    }

    @PostMapping("/cancel")
    public void cancelPayment(@RequestBody CancelPaymentCommand command) {
        paymentApplicationService.cancelPayment(command);
    }
}
