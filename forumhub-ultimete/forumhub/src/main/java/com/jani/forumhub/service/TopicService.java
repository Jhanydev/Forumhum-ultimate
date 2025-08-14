package com.jani.forumhub.service;

import com.jani.forumhub.domain.tag.Tag;
import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.domain.topic.TopicStatus;
import com.jani.forumhub.domain.user.User;
import com.jani.forumhub.repository.*;
import com.jani.forumhub.repository.spec.TopicSpecifications;
import com.jani.forumhub.web.dto.TopicDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TopicLikeRepository likeRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository,
                        TagRepository tagRepository, TopicLikeRepository likeRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public TopicResponse create(CreateTopicRequest req, String authorEmail) {
        User author = userRepository.findByEmail(authorEmail).orElseThrow();
        Topic topic = Topic.builder()
                .title(req.title())
                .message(req.message())
                .course(req.course())
                .author(author)
                .status(TopicStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();
        if (req.tags() != null) {
            req.tags().forEach(name -> topic.getTags().add(resolveTag(name)));
        }
        topicRepository.save(topic);
        return map(topic);
    }

    public Page<TopicResponse> list(String q, String course, String authorName, TopicStatus status,
                                    LocalDateTime from, LocalDateTime to, int page, int size) {
        Specification<Topic> spec = Specification.where(TopicSpecifications.titleContains(q))
                .and(TopicSpecifications.courseEquals(course))
                .and(TopicSpecifications.authorNameContains(authorName))
                .and(TopicSpecifications.statusEquals(status))
                .and(TopicSpecifications.createdFrom(from))
                .and(TopicSpecifications.createdTo(to));
        return topicRepository.findAll(spec, PageRequest.of(page, size)).map(this::map);
    }

    public TopicResponse get(Long id) {
        return topicRepository.findById(id).map(this::map).orElse(null);
    }

    @Transactional
    public TopicResponse update(Long id, UpdateTopicRequest req) {
        var topic = topicRepository.findById(id).orElseThrow();
        topic.setTitle(req.title());
        topic.setMessage(req.message());
        topic.setCourse(req.course());
        topic.setStatus(req.status());
        topic.getTags().clear();
        if (req.tags() != null) {
            req.tags().forEach(name -> topic.getTags().add(resolveTag(name)));
        }
        return map(topic);
    }

    @Transactional
    public TopicResponse setStatus(Long id, TopicStatus status){
        var topic = topicRepository.findById(id).orElseThrow();
        topic.setStatus(status);
        return map(topic);
    }

    @Transactional
    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

    private Tag resolveTag(String name){
        return tagRepository.findByNameIgnoreCase(name.trim()).orElseGet(() ->
            tagRepository.save(Tag.builder().name(name.trim()).build())
        );
    }

    private TopicResponse map(Topic t) {
        List<String> tagNames = t.getTags().stream().map(Tag::getName).sorted().toList();
        return new TopicResponse(t.getId(), t.getTitle(), t.getMessage(), t.getCreatedAt(),
                t.getStatus(), t.getAuthor().getName(), t.getCourse(), tagNames, t.getLikeCount());
    }
}
