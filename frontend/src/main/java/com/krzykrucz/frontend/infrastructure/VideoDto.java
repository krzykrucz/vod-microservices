package com.krzykrucz.frontend.infrastructure;

import lombok.Data;

@Data
public class VideoDto {

    private String title;

    private PriceDto price;

    @Data
    private class PriceDto {
        private String pretty;
    }
}


