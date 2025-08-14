package com.jani.forumhub.domain.topic;

import com.jani.forumhub.domain.tag.Tag;
import com.jani.forumhub.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topics")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 4000)
    private String message;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String course;

    @ManyToMany
    @JoinTable(name="topics_tags",
            joinColumns=@JoinColumn(name="topic_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id"))
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable=false)
    @Builder.Default
    private Integer likeCount = 0;
}
