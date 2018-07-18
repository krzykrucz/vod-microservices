package com.krzykrucz.frontend.infrastructure;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.Map;

@Getter
public class ApiEndpoint<T> {

    private final String url;

    private final Class<T> responseType;

    private final Map<String, String> pathVariables;

    private ApiEndpoint(String url, Class<T> responseType, Map<String, String> pathVariables) {
        this.url = url;
        this.responseType = responseType;
        this.pathVariables = pathVariables;
    }

    static <T> ApiEndpoint<T> define(String url, Class<T> responseType) {
        return new ApiEndpoint<>(url, responseType, ImmutableMap.of());
    }

    public ApiEndpoint<T> withPathVariable(String key, String value) {
        final Map<String, String> newPathVariables = ImmutableMap.<String, String>builder()
                .putAll(pathVariables)
                .put(key, value)
                .build();
        return new ApiEndpoint<>(url, responseType, newPathVariables);
    }

}
