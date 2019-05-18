package com.krzykrucz.movies.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.krzykrucz.movies.domain.VideoDetails;
import com.krzykrucz.movies.domain.VideoId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.money.Money;

@AllArgsConstructor
@Getter
class VideoInfoDTO {

    private final VideoId videoId;

    private final String title;

    @JsonSerialize(using = MoneySerializer.class)
    private final Money price;

    static VideoInfoDTO fromVideoInfo(VideoDetails videoDetails) {
        return new VideoInfoDTO(videoDetails.getVideoId(), videoDetails.getTitle(), videoDetails.getPrice().getMoney());
    }
}
