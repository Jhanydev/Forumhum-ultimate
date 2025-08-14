package com.jani.forumhub.web;

import com.jani.forumhub.service.ReplyService;
import com.jani.forumhub.web.dto.TopicDtos.ReplyRequest;
import com.jani.forumhub.web.dto.TopicDtos.ReplyResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics/{topicId}/replies")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping
    public List<ReplyResponse> list(@PathVariable Long topicId){
        return replyService.list(topicId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ReplyResponse> add(@PathVariable Long topicId, @RequestBody @Valid ReplyRequest req, Authentication auth){
        var email = (String) auth.getPrincipal();
        return ResponseEntity.ok(replyService.addReply(topicId, req, email));
    }
}
