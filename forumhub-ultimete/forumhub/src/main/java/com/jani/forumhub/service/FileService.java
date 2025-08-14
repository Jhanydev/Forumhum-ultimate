package com.jani.forumhub.service;

import com.jani.forumhub.domain.topic.Attachment;
import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.repository.AttachmentRepository;
import com.jani.forumhub.repository.TopicRepository;
import com.jani.forumhub.repository.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    private final AttachmentRepository attachmentRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final Path root = Path.of("./uploads");

    public FileService(AttachmentRepository attachmentRepository, TopicRepository topicRepository, UserRepository userRepository) throws IOException {
        this.attachmentRepository = attachmentRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        Files.createDirectories(root);
    }

    @Transactional
    public List<Attachment> list(Long topicId){
        Topic t = topicRepository.findById(topicId).orElseThrow();
        return attachmentRepository.findByTopic(t);
    }

    @Transactional
    public Attachment upload(Long topicId, MultipartFile file, String email) throws IOException {
        Topic t = topicRepository.findById(topicId).orElseThrow();
        var uploader = userRepository.findByEmail(email).orElseThrow();
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path dest = root.resolve(filename);
        Files.copy(file.getInputStream(), dest);
        var att = Attachment.builder()
                .topic(t)
                .uploader(uploader)
                .filename(filename)
                .contentType(file.getContentType() == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : file.getContentType())
                .uploadedAt(LocalDateTime.now())
                .build();
        return attachmentRepository.save(att);
    }

    public FileSystemResource getFile(String filename){
        return new FileSystemResource(root.resolve(filename));
    }
}
