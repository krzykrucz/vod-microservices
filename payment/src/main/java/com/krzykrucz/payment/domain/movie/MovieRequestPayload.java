package com.krzykrucz.payment.domain.movie;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MovieRequestPayload {

    private final Map<MovieRequestProperty, String> properties;

    public Optional<String> getProperty(MovieRequestProperty property) {
        if (properties.containsKey(property)) {
            return Optional.of(properties.get(property));
        }
        return Optional.empty();
    }
}
