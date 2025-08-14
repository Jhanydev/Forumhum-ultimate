package com.jani.forumhub.web.dto;

import com.jani.forumhub.domain.topic.TopicStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class TopicDtos {
    public record CreateTopicRequest(
            @NotBlank String title,
            @NotBlank String message,
            @NotBlank String course,
            List<String> tags
    ){}

    public record UpdateTopicRequest(
            @NotBlank String title,
            @NotBlank String message,
            @NotBlank String course,
            @NotNull TopicStatus status,
            List<String> tags
    ){}

    public record TopicResponse(
            Long id,
            String title,
            String message,
            LocalDateTime createdAt,
            TopicStatus status,
            String authorName,
            String course,
            List<String> tags,
            Integer likeCount
    ){}

    public record ReplyRequest(@NotBlank String message){}

    public record ReplyResponse(Long id, String authorName, String message, LocalDateTime createdAt){}

    public record AttachmentResponse(Long id, String filename, String contentType, LocalDateTime uploadedAt){}
}
