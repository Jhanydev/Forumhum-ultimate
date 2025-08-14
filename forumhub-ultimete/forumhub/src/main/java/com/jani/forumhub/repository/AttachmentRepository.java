package com.jani.forumhub.repository;

import com.jani.forumhub.domain.topic.Attachment;
import com.jani.forumhub.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTopic(Topic topic);
}
