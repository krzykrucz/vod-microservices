package com.krzykrucz.payment.domain.payment;

import com.krzykrucz.payment.domain.movie.Movie;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Getter
public class Payment {

    private final static Duration MAX_PAYMENT_DURATION = Duration.ofMinutes(15);

    @Id
    private final PaymentId paymentId;
    private PayerId payerId;
    private final PaymentView paymentView;
    private final Movie paidMovie;
    private final Instant creationTime;

    // TODO converter
    private final Clock clock;
    private Status status = Status.CREATED;

    public Payment(PaymentId paymentId, PaymentView paymentView, Movie paidMovie, Clock clock) {
        this.paymentId = paymentId;
        this.paymentView = paymentView;
        this.paidMovie = paidMovie;
        this.creationTime = clock.instant();
        this.clock = clock;
    }

    public Payment(PaymentId paymentId, PaymentView paymentView, Movie paidMovie) {
        this(paymentId, paymentView, paidMovie, Clock.systemUTC());
    }

    private Payment() {
        paymentId = null;
        paidMovie = null;
        paymentView = null;
        creationTime = null;
        clock = null;
    }

    public void confirm() {
        this.status = Status.CONFIRMED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public void executeForPayer(PayerId payerId) {
        this.payerId = payerId;
        this.status = Status.EXECUTED;
    }

    public boolean isPurgable() {
        final Instant now = clock.instant();
        final Duration paymentLifeTime = Duration.between(creationTime, now);
        return paymentLifeTime.compareTo(MAX_PAYMENT_DURATION) > 0;
    }

    public Optional<PayerId> getPayerId() {
        return Optional.ofNullable(payerId);
    }

    public enum Status {
        CREATED,
        CONFIRMED,
        EXECUTED,
        CANCELLED,
        REJECTED;
    }

}
