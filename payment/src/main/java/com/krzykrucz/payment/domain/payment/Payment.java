package com.krzykrucz.payment.domain.payment;

import com.krzykrucz.payment.domain.Movie;
import lombok.Getter;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Getter
public class Payment {

    private final static Duration MAX_PAYMENT_DURATION = Duration.ofMinutes(15);

    private final PaymentId paymentId;
    private final PayerId payerId;
    private final PaymentView paymentView;
    private final Movie paidMovie;
    private final Instant creationTime;
    private final Clock clock;
    private Status status = Status.CREATED;

    public Payment(PaymentId paymentId, PayerId payerId, PaymentView paymentView, Movie paidMovie, Clock clock) {
        this.paymentId = paymentId;
        this.payerId = payerId;
        this.paymentView = paymentView;
        this.paidMovie = paidMovie;
        this.creationTime = clock.instant();
        this.clock = clock;
    }

    public Payment(PaymentId paymentId, PayerId payerId, PaymentView paymentView, Movie paidMovie) {
        this(paymentId, payerId, paymentView, paidMovie, Clock.systemUTC());
    }

    public void confirm() {
        this.status = Status.CONFIRMED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public void execute() {
        this.status = Status.EXECUTED;
    }

    public boolean isPurgable() {
        final Instant now = clock.instant();
        final Duration paymentLifeTime = Duration.between(creationTime, now);
        return paymentLifeTime.compareTo(MAX_PAYMENT_DURATION) > 0;
    }

    public enum Status {
        CREATED,
        CONFIRMED,
        EXECUTED,
        CANCELLED,
        REJECTED;
    }

}
