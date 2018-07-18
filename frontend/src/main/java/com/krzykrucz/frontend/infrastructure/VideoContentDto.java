package com.krzykrucz.frontend.infrastructure;

import lombok.Data;

@Data
public class VideoContentDto {
    private byte[] content;

    private String error;
}
