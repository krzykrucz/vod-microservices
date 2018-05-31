package com.krzykrucz.payment.application;

import com.krzykrucz.payment.domain.customer.CustomerName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
class CreateCustomerCommand {

    private UserName userName;

    public CustomerName getCustomerName() {
        return new CustomerName(userName.getName());
    }

    @Data
    private class UserName {
        private String name;
    }

}
