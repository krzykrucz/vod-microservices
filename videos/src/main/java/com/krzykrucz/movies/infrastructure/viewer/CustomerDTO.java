package com.krzykrucz.movies.infrastructure.viewer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
class CustomerDTO {

    private String name;

    private Set<MovieDTO> boughtMovies;

}
