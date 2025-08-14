package com.jani.forumhub.repository;

import com.jani.forumhub.domain.topic.Reply;
import com.jani.forumhub.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByTopicOrderByCreatedAtAsc(Topic topic);
}
