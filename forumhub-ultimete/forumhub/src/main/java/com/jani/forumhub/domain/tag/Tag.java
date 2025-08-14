package com.jani.forumhub.domain.tag;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(name="uk_tag_name", columnNames = "name"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true)
    private String name;
}
