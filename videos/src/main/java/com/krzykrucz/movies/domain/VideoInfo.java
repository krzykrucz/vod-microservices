package com.krzykrucz.movies.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "videoId")
@AllArgsConstructor
public class VideoInfo {

    private final VideoId videoId;

    private String title;

    private Price price;

}
