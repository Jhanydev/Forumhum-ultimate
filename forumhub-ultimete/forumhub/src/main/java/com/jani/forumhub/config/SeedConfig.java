package com.jani.forumhub.config;

import com.jani.forumhub.domain.topic.Topic;
import com.jani.forumhub.domain.topic.TopicStatus;
import com.jani.forumhub.domain.user.Role;
import com.jani.forumhub.domain.user.User;
import com.jani.forumhub.repository.TopicRepository;
import com.jani.forumhub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class SeedConfig {

    @Bean
    CommandLineRunner seedUsers(UserRepository users, PasswordEncoder encoder, TopicRepository topics){
        return args -> {
            if (users.count() == 0){
                var admin = User.builder().name("Admin").email("admin@forumhub.dev")
                        .password(encoder.encode("123456")).role(Role.ROLE_ADMIN).build();
                var mod = User.builder().name("Moderadora").email("mod@forumhub.dev")
                        .password(encoder.encode("123456")).role(Role.ROLE_MODERATOR).build();
                var user = User.builder().name("Janilucia").email("user@forumhub.dev")
                        .password(encoder.encode("123456")).role(Role.ROLE_USER).build();
                users.save(admin); users.save(mod); users.save(user);

                topics.save(Topic.builder().title("Boas-vindas ao FórumHub")
                        .message("Apresente-se e diga o que está estudando!")
                        .course("Onboarding").author(admin)
                        .status(TopicStatus.OPEN).createdAt(LocalDateTime.now()).build());
            }
        };
    }
}
