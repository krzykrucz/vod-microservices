package com.krzykrucz.payment.domain.customer;

public interface CustomerProvider {

    Customer getCustomerByName(CustomerName name);

}
