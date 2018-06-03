package com.krzykrucz.payment;

import com.paypal.base.rest.APIContext;
import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalTestConfig {

    APIContext apiContext() {
        // TODO
        final APIContext mock = Mockito.mock(APIContext.class);
//        Mockito.when(mock.)
        return mock;
    }

}
