package com.krzykrucz.payment.infrastructure.movie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "videos")
interface VideosClient {

    @GetMapping("/videos/{title}")
    VideoInfoDTO getVideoInfo(@PathVariable("title") String title);

}
