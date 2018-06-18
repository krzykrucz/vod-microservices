package com.krzykrucz.payment

import com.krzykrucz.payment.application.customer.CreateCustomerCommand
import com.krzykrucz.payment.application.customer.CustomerCommandHandler
import com.krzykrucz.payment.application.customer.CustomerNameDTO
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

    def "should follow happy path"() {
        given:
        videosClient.getVideoInfo('Harry Potter') >> new VideoInfoDTO('Harry Potter', USD_10)
        payPalService.createPayment(_, _, _, _) >> stubPayment(A_PAYMENT_ID, 'viewUrl')

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

        then:
        buyResponse.status == OK
        buyResponse.json.viewUrl == 'viewUrl'
        successResponse.status == OK
        1 * payPalService.createPayment(USD_10, '', 'cancelUrl', 'successUrl')
        1 * payPalService.executePayment(new PaymentId(A_PAYMENT_ID), new PayerId(A_PAYER_ID))
    }

    Payment stubPayment(paymentId, viewUrl) {
        def payment = new Payment()
        payment.setId(paymentId)
        payment.setLinks([new Links(viewUrl, 'approval_url')])
        payment
    }

    private createCustomer(name) {
        def command = new CreateCustomerCommand(new CustomerNameDTO(name))
        handler.receive(MessageBuilder.withPayload(command).build())
    }

}
