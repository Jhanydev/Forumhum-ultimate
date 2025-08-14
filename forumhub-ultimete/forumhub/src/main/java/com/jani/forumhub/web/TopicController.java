package com.jani.forumhub.web;

import com.jani.forumhub.domain.topic.TopicStatus;
import com.jani.forumhub.service.TopicService;
import com.jani.forumhub.web.dto.TopicDtos.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public Page<TopicResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) TopicStatus status,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        LocalDateTime f = from == null ? null : LocalDateTime.parse(from);
        LocalDateTime t = to == null ? null : LocalDateTime.parse(to);
        return topicService.list(q, course, authorName, status, f, t, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> get(@PathVariable Long id){
        var t = topicService.get(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<TopicResponse> create(@RequestBody @Valid CreateTopicRequest req, Authentication auth){
        var email = (String) auth.getPrincipal();
        return ResponseEntity.ok(topicService.create(req, email));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<TopicResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicRequest req){
        return ResponseEntity.ok(topicService.update(id, req));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<TopicResponse> setStatus(@PathVariable Long id, @RequestParam TopicStatus status){
        return ResponseEntity.ok(topicService.setStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
