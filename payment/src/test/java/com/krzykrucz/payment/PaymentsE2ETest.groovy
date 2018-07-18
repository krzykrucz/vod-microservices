package com.krzykrucz.payment

import com.krzykrucz.payment.application.customer.CreateCustomerCommand
import com.krzykrucz.payment.application.customer.CustomerCommandHandler
import com.krzykrucz.payment.domain.customer.CustomerName
import com.krzykrucz.payment.domain.customer.CustomerRepository
import com.krzykrucz.payment.domain.payment.PayerId
import com.krzykrucz.payment.domain.payment.PaymentId
import com.krzykrucz.payment.domain.payment.paypal.PayPalService
import com.krzykrucz.payment.infrastructure.movie.VideoInfoDTO
import com.krzykrucz.payment.infrastructure.movie.VideosClient
import com.paypal.api.payments.Links
import com.paypal.api.payments.Payment
import org.joda.money.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder

import static org.joda.money.CurrencyUnit.USD
import static org.springframework.http.HttpStatus.OK

class PaymentsE2ETest extends AbstractE2ESpec {

    static final Money USD_10 = Money.of(USD, 10)

    static final A_PAYMENT_ID = 'some-payment-id'
    static final A_PAYER_ID = 'some-payer-id'

    @Autowired
    CustomerCommandHandler handler

    @Autowired
    VideosClient videosClient

    @Autowired
    PayPalService payPalService

    @Autowired
    CustomerRepository customerRepository

    def "should follow happy path"() {
        given:
        videosClient.getVideoInfo('Harry Potter') >> new VideoInfoDTO('Harry Potter', USD_10)
        def stubPayment = stubPayment(A_PAYMENT_ID, 'viewUrl')

        createCustomer 'Barbossa'

        when:
        def buyRequest = [
                movieTitle         : 'Harry Potter',
                successViewUrl     : 'successUrl',
                cancelViewUrl      : 'cancelUrl',
                currentCustomerName: 'Barbossa'
        ]
        def buyResponse = post('/payment/buy', buyRequest)

        def executePaymentRequest = [
                paymentId          : A_PAYMENT_ID,
                payerId            : A_PAYER_ID,
                currentCustomerName: 'Barbossa'
        ]
        def successResponse = post('/payment/success', executePaymentRequest)

        then: "requests should be successful"
        buyResponse.status == OK
        buyResponse.json.viewUrl == 'viewUrl'
        successResponse.status == OK
        1 * payPalService.createPayment(USD_10, 'Payment for Harry Potter', 'cancelUrl', 'successUrl') >> stubPayment
        1 * payPalService.executePayment(new PaymentId(A_PAYMENT_ID), new PayerId(A_PAYER_ID))

        and: "customer should be in good state"
        def customer = getCustomer 'Barbossa'
        customer.get().purchasedMovies*.title == ['Harry Potter']
        customer.get().purchasedMovies*.price.money == [USD_10]

    }

    static Payment stubPayment(paymentId, viewUrl) {
        def payment = new Payment()
        payment.setId(paymentId)
        payment.setLinks([new Links(viewUrl, 'approval_url')])
        payment
    }

    private createCustomer(name) {
        def command = new CreateCustomerCommand(name)
        handler.receive(MessageBuilder.withPayload(command).build())
    }

    private getCustomer(name) {
        customerRepository.findOne new CustomerName(name)
    }

}
