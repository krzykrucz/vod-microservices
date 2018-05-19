package com.krzykrucz.movies.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class VideoId implements Serializable {

    private final UUID uuid;
}
