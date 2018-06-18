package com.krzykrucz.payment

import com.krzykrucz.payment.domain.payment.paypal.PayPalService
import com.krzykrucz.payment.infrastructure.movie.VideosClient
import com.paypal.base.rest.APIContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class TestConfig {

    private final DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    PayPalService payPalService() {
        detachedMockFactory.Mock(PayPalService)
    }

    @Bean
    VideosClient videosClient() {
        detachedMockFactory.Stub(VideosClient)
    }

    @Bean
    APIContext apiContext() {
        detachedMockFactory.Stub(APIContext)
    }

}
