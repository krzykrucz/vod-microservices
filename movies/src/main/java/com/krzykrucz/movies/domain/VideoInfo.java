package com.krzykrucz.movies.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Duration;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "videoId")
@AllArgsConstructor
public class VideoInfo {

    private final VideoId videoId;

    private String title;

    private Price price;

}
