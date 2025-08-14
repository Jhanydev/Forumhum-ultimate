package com.jani.forumhub.service;

import com.jani.forumhub.domain.topic.Reply;
import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.repository.ReplyRepository;
import com.jani.forumhub.repository.TopicRepository;
import com.jani.forumhub.repository.UserRepository;
import com.jani.forumhub.web.dto.TopicDtos.ReplyRequest;
import com.jani.forumhub.web.dto.TopicDtos.ReplyResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public ReplyService(ReplyRepository replyRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReplyResponse addReply(Long topicId, ReplyRequest req, String email){
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        var author = userRepository.findByEmail(email).orElseThrow();
        var r = Reply.builder()
                .topic(topic)
                .author(author)
                .message(req.message())
                .createdAt(LocalDateTime.now())
                .build();
        replyRepository.save(r);
        return map(r);
    }

    public List<ReplyResponse> list(Long topicId){
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        return replyRepository.findByTopicOrderByCreatedAtAsc(topic).stream().map(this::map).toList();
    }

    private ReplyResponse map(Reply r){
        return new ReplyResponse(r.getId(), r.getAuthor().getName(), r.getMessage(), r.getCreatedAt());
    }
}
