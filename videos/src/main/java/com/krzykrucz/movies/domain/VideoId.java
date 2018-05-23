package com.krzykrucz.movies.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class VideoId implements Serializable {

    private final UUID uuid;
}
