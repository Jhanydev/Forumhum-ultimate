package com.jani.forumhub.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jani.forumhub.web.dto.AuthDtos.LoginRequest;
import com.jani.forumhub.web.dto.AuthDtos.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiIntegrationTests {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void registerLoginCreateTopicFlow() throws Exception {
        String email = "inttest@example.com";
        // register
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new RegisterRequest("Teste", email, "123456"))))
                .andExpect(status().isOk());

        // login
        var res = mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new LoginRequest(email, "123456"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = om.readTree(res).get("token").asText();

        // create topic
        String body = "{ "title":"Titulo","message":"Mensagem","course":"Spring","tags":["java","spring"] }";
        mvc.perform(post("/api/topics").header("Authorization","Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Titulo"))
                .andExpect(jsonPath("$.tags[0]").exists());
    }
}
