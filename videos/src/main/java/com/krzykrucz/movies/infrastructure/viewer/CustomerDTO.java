package com.krzykrucz.movies.infrastructure.viewer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CustomerDTO {

    private CustomerNameDTO customerName;

    private Set<MovieDTO> purchasedMovies;

    String getCustomerName() {
        return customerName.getName();
    }

    @Data
    @NoArgsConstructor
    private class CustomerNameDTO {
        private String name;
    }
}
