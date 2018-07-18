package com.krzykrucz.movies.application;

import lombok.Getter;

@Getter
class VideoContentResponse {

    private byte[] content;
    private String error;

    VideoContentResponse(byte[] content) {
        this.content = content;
    }

    VideoContentResponse(String error) {
        this.error = error;
    }
}
