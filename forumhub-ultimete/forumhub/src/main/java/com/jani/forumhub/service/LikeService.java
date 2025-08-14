package com.jani.forumhub.service;

import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.domain.topic.TopicLike;
import com.jani.forumhub.repository.TopicLikeRepository;
import com.jani.forumhub.repository.TopicRepository;
import com.jani.forumhub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
    private final TopicLikeRepository likeRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public LikeService(TopicLikeRepository likeRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public int like(Long topicId, String email){
        var topic = topicRepository.findById(topicId).orElseThrow();
        var user = userRepository.findByEmail(email).orElseThrow();
        likeRepository.findByTopicAndUser(topic, user).ifPresent(l -> {throw new IllegalStateException("Já curtiu");});
        likeRepository.save(TopicLike.builder().topic(topic).user(user).build());
        topic.setLikeCount((int) likeRepository.countByTopic(topic));
        return topic.getLikeCount();
    }

    @Transactional
    public int unlike(Long topicId, String email){
        var topic = topicRepository.findById(topicId).orElseThrow();
        var user = userRepository.findByEmail(email).orElseThrow();
        var like = likeRepository.findByTopicAndUser(topic, user).orElse(null);
        if (like != null) likeRepository.delete(like);
        topic.setLikeCount((int) likeRepository.countByTopic(topic));
        return topic.getLikeCount();
    }
}
