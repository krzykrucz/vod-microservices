package com.krzykrucz.frontend.ui;

import com.krzykrucz.frontend.infrastructure.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Controller
public class VideoController {

    private final ApiGatewayClient client;

    public VideoController(ApiGatewayClient client) {
        this.client = client;
    }

    @GetMapping("/videos")
    String videos(@ModelAttribute("username") String username, Model model) {
        if (username == null || username.isEmpty()) {
            return "login";
        }
        final ResponseEntity<VideoDto[]> response = client.get(ApiEndpoints.GET_ALL_VIDEOS);
        model.addAttribute("username", username);
        if (!response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("videos", emptyList());
        } else {
            model.addAttribute("videos", asList(response.getBody()));
        }
        return "videos";
    }

    @PostMapping("/watch/{title}")
    String watch(@ModelAttribute("username") String username, @PathVariable("title") String videoTitle, Model model, RedirectAttributes redirectAttributes) {
        if (videoTitle == null || videoTitle.isEmpty()) {
            return "login";
        }
        final ApiEndpoint<VideoContentDto> getVideoContentEndpoint = ApiEndpoints.GET_VIDEO_CONTENT
                .withPathVariable("title", videoTitle)
                .withPathVariable("viewerName", "Barbossa");
        final ResponseEntity<VideoContentDto> response = client.get(getVideoContentEndpoint);
        if (response.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            redirectAttributes.addFlashAttribute("videoTitle", videoTitle);
            return "redirect:/buy/Barbossa";
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("error", "Error watching " + videoTitle);
            return "videos";
        }
        model.addAttribute("title", videoTitle);
        model.addAttribute("content", response.getBody());
        return "watch";
    }

    @GetMapping("/watch/{title}")
    String watch(@PathVariable("title") String videoTitle, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        final ApiEndpoint<VideoContentDto> getVideoContentEndpoint = ApiEndpoints.GET_VIDEO_CONTENT
                .withPathVariable("title", videoTitle)
                .withPathVariable("viewerName", "Barbossa");
        final ResponseEntity<VideoContentDto> response = client.get(ApiEndpoints.GET_VIDEO_CONTENT.withPathVariable("title", videoTitle));
        if (response.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            redirectAttributes.addFlashAttribute("videoTitle", videoTitle);
            return "redirect:/buy/Barbossa";
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("error", "Error watching " + videoTitle);
            return "videos";
        }
        model.addAttribute("title", videoTitle);
        model.addAttribute("content", response.getBody());
        return "watch";
    }

}
