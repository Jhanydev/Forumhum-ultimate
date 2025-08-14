package com.jani.forumhub.web;

import com.jani.forumhub.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics/{topicId}")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Integer> like(@PathVariable Long topicId, Authentication auth){
        var email = (String) auth.getPrincipal();
        return ResponseEntity.ok(likeService.like(topicId, email));
    }

    @DeleteMapping("/like")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Integer> unlike(@PathVariable Long topicId, Authentication auth){
        var email = (String) auth.getPrincipal();
        return ResponseEntity.ok(likeService.unlike(topicId, email));
    }
}
