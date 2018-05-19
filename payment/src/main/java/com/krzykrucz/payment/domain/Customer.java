package com.krzykrucz.payment.domain;


import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(of = "customerId")
@RequiredArgsConstructor
public class Customer {

    private final CustomerId customerId;

    private final CustomerName customerName;

    private Set<Movie> purchasedMovies = HashSet.empty();

}
