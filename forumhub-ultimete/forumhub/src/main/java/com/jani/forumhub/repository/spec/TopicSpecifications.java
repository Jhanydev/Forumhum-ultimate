package com.jani.forumhub.repository.spec;

import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.domain.topic.TopicStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TopicSpecifications {
    public static Specification<Topic> titleContains(String q){
        return (root, query, cb) -> q == null ? null : cb.like(cb.lower(root.get("title")), "%" + q.toLowerCase() + "%");
    }
    public static Specification<Topic> courseEquals(String course){
        return (root, query, cb) -> course == null ? null : cb.equal(root.get("course"), course);
    }
    public static Specification<Topic> statusEquals(TopicStatus status){
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }
    public static Specification<Topic> createdFrom(LocalDateTime from){
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), from);
    }
    public static Specification<Topic> createdTo(LocalDateTime to){
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("createdAt"), to);
    }
    public static Specification<Topic> authorNameContains(String name){
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.join("author").get("name")), "%" + name.toLowerCase() + "%");
    }
}
