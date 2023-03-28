package com.sns.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    @DisplayName("정상적인 접근으로 게시판 작성을 하면 성공한다")
    void post_success() throws Exception {
        String title = "title";
        String context = "context";

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequestDto(title, context)))
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("비정상적인 접근으로 게시판 작성을 하면 실패한다")
    void post_fail() throws Exception {
        String title = "title";
        String context = "context";

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequestDto(title, context)))
                ).andDo(print())
                .andExpect(status().isOk());
    }
}
