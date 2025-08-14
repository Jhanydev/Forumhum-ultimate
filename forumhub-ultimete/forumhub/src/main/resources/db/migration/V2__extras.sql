CREATE TABLE IF NOT EXISTS tags(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS topics_tags(
    topic_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY(topic_id, tag_id),
    CONSTRAINT fk_tt_topic FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE,
    CONSTRAINT fk_tt_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS replies(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    CONSTRAINT fk_replies_topic FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE,
    CONSTRAINT fk_replies_author FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS topic_likes(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT uk_like_topic_user UNIQUE (topic_id,user_id),
    CONSTRAINT fk_likes_topic FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE topics ADD COLUMN IF NOT EXISTS like_count INT NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS attachments(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    uploader_id BIGINT NOT NULL,
    filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(120) NOT NULL,
    uploaded_at DATETIME NOT NULL,
    CONSTRAINT fk_att_topic FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE,
    CONSTRAINT fk_att_uploader FOREIGN KEY (uploader_id) REFERENCES users(id)
);
