package com.krzykrucz.payment

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spockmvc.SpockMvcSpec

//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
@SpringBootTest
@ContextConfiguration(classes = [PaymentApplication])
@ActiveProfiles("test")
abstract class AbstractE2ESpec extends SpockMvcSpec {

    @Override
    MockMvc buildMockMvc(WebApplicationContext wac) {
        MockMvcBuilders
                .webAppContextSetup(wac)
//                .apply(springSecurity())
                .build()
    }
}
