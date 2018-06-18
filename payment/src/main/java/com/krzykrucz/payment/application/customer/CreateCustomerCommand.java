package com.krzykrucz.payment.application.customer;

import com.krzykrucz.payment.domain.customer.CustomerName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCommand {

    private CustomerNameDTO userName;

    public CustomerName getCustomerName() {
        return new CustomerName(userName.getName());
    }

}
