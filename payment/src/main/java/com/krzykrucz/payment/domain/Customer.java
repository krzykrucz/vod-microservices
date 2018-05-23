package com.krzykrucz.payment.domain;


import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@EqualsAndHashCode(of = "customerId")
@RequiredArgsConstructor
public class Customer {

    private final CustomerId customerId;

    private final CustomerName customerName;

    private Set<Movie> purchasedMovies = Sets.newHashSet();

}
