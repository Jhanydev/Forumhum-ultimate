package com.jani.forumhub.web;

import com.jani.forumhub.domain.user.Role;
import com.jani.forumhub.domain.user.User;
import com.jani.forumhub.repository.UserRepository;
import com.jani.forumhub.security.JwtService;
import com.jani.forumhub.web.dto.AuthDtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterRequest req){
        if (users.findByEmail(req.email()).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        var u = User.builder()
                .name(req.name())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .role(Role.ROLE_USER)
                .build();
        users.save(u);
        return ResponseEntity.ok(new TokenResponse(jwt.generateToken(u.getEmail())));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest req){
        var u = users.findByEmail(req.email()).orElse(null);
        if (u == null || !encoder.matches(req.password(), u.getPassword())){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new TokenResponse(jwt.generateToken(u.getEmail())));
    }
}
