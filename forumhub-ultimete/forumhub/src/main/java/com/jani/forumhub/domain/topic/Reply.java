package com.jani.forumhub.domain.topic;

import com.jani.forumhub.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "replies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="topic_id")
    private Topic topic;

    @ManyToOne(optional=false) @JoinColumn(name="author_id")
    private User author;

    @Column(nullable=false, length=4000)
    private String message;

    @Column(nullable=false)
    private LocalDateTime createdAt;
}
