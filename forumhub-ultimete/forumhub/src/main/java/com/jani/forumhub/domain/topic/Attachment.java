package com.jani.forumhub.domain.topic;

import com.jani.forumhub.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Attachment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="topic_id")
    private Topic topic;

    @ManyToOne(optional=false) @JoinColumn(name="uploader_id")
    private User uploader;

    @Column(nullable=false)
    private String filename;

    @Column(nullable=false)
    private String contentType;

    @Column(nullable=false)
    private LocalDateTime uploadedAt;
}
