package com.jani.forumhub.domain.topic;

import com.jani.forumhub.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic_likes", uniqueConstraints = @UniqueConstraint(name="uk_like_topic_user", columnNames = {"topic_id","user_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TopicLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="topic_id")
    private Topic topic;

    @ManyToOne(optional=false) @JoinColumn(name="user_id")
    private User user;
}
