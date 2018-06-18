package com.krzykrucz.payment.domain.payment;

import com.krzykrucz.payment.domain.movie.Movie;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Getter
public class Payment {

    private final static Duration MAX_PAYMENT_DURATION = Duration.ofMinutes(15);

    @Id
    private final PaymentId paymentId;
    private PayerId payerId;
    private final PaymentView paymentView;
    private final Movie paidMovie;
    private final Instant creationTime;
    private Status status = Status.CREATED;
    @Transient
    private final Clock timeSource;

    public Payment(PaymentId paymentId, PaymentView paymentView, Movie paidMovie) {
        this.paymentId = paymentId;
        this.paymentView = paymentView;
        this.paidMovie = paidMovie;
        this.timeSource = Clock.systemUTC();
        this.creationTime = timeSource.instant();
    }

    Payment(PaymentId paymentId, PaymentView paymentView, Movie paidMovie, Clock timeSource) {
        this.paymentId = paymentId;
        this.paymentView = paymentView;
        this.paidMovie = paidMovie;
        this.timeSource = timeSource;
        this.creationTime = timeSource.instant();
    }

    private Payment() {
        paymentId = null;
        paidMovie = null;
        paymentView = null;
        creationTime = null;
        timeSource = Clock.systemUTC();
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
        final Instant now = timeSource.instant();
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
