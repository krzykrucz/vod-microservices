package com.krzykrucz.payment.application.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krzykrucz.payment.domain.customer.CustomerName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCommand {

    private String userName;

    @JsonProperty("userName")
    void setUserName(Map<String, Object> userName) {
        this.userName = (String) userName.get("name");
    }

    public CustomerName getCustomerName() {
        return new CustomerName(userName);
    }

}
