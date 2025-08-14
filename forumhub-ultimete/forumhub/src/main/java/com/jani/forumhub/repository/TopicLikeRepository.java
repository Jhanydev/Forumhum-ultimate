package com.jani.forumhub.repository;

import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.domain.topic.TopicLike;
import com.jani.forumhub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicLikeRepository extends JpaRepository<TopicLike, Long> {
    Optional<TopicLike> findByTopicAndUser(Topic topic, User user);
    long countByTopic(Topic topic);
}
