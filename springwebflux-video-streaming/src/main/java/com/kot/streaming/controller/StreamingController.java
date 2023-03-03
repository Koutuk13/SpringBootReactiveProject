package com.kot.streaming.controller;

import com.kot.streaming.service.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video")
public class StreamingController {

    @Autowired
    private StreamingService streamingService;

    @GetMapping(value = "/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range){
        System.out.println("range in bytes :: "+ range);
        return streamingService.getVideo(title);
    }
}
