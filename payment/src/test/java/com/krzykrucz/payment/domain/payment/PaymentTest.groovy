package com.krzykrucz.payment.domain.payment

import com.krzykrucz.payment.domain.movie.Movie
import spock.lang.Specification

import java.time.Clock
import java.time.Instant

import static com.krzykrucz.payment.domain.movie.Price.zero
import static org.joda.money.CurrencyUnit.USD

class PaymentTest extends Specification {

    final static PAYMENT_ID = new PaymentId('payment-id')
    static final PAYMENT_VIEW = new PaymentView('view')

    def clock = Stub(Clock)

    def "should be purgable"() {
        given:
        clock.instant() >>> [Instant.parse('2017-01-03T10:15:00Z'), Instant.parse(purgingTime)]
        def payment = new Payment(PAYMENT_ID, PAYMENT_VIEW, new Movie(zero(USD), 'title'), clock)

        expect:
        payment.purgable == expectedToBePurgable

        where:
        purgingTime            || expectedToBePurgable
        '2017-01-03T10:31:00Z' || true
        '2017-01-03T10:29:00Z' || false
    }

}
