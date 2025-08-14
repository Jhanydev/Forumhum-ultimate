package com.jani.forumhub.web;

import com.jani.forumhub.domain.topic.Attachment;
import com.jani.forumhub.service.FileService;
import com.jani.forumhub.web.dto.TopicDtos.AttachmentResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/topics/{topicId}/attachments")
    public List<AttachmentResponse> list(@PathVariable Long topicId){
        return fileService.list(topicId).stream()
                .map(this::map)
                .toList();
    }

    @PostMapping(value="/api/topics/{topicId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public AttachmentResponse upload(@PathVariable Long topicId, @RequestParam("file") MultipartFile file, Authentication auth) throws IOException {
        var email = (String) auth.getPrincipal();
        var att = fileService.upload(topicId, file, email);
        return map(att);
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<FileSystemResource> get(@PathVariable String filename){
        var res = fileService.getFile(filename);
        if (!res.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="" + res.getFilename() + """)
                .contentLength(res.contentLength())
                .body(res);
    }

    private AttachmentResponse map(Attachment a){
        return new AttachmentResponse(a.getId(), a.getFilename(), a.getContentType(), a.getUploadedAt());
    }
}
