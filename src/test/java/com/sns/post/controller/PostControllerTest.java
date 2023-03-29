package com.sns.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.common.exception.ErrorCode;
import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.dto.request.PostUpdateRequestDto;
import com.sns.post.exception.PostException;
import com.sns.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mockMvc.perform(post("/api/v1/post/create")
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

        mockMvc.perform(post("/api/v1/post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequestDto(title, context)))
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    @DisplayName("게시판 수정 요청하면 정상적으로 작동한다")
    void post_update_success() throws Exception {
        String title = "title";
        String context = "context";

        mockMvc.perform(put("/api/v1/post/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostUpdateRequestDto(title, context)))
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("비 정상적인 접근으로 게시판 수정 요청하면 정상적으로 작동하지 않는다")
    void post_update_fail() throws Exception {
        String title = "title";
        String context = "context";

        mockMvc.perform(put("/api/v1/post/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostUpdateRequestDto(title, context)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser
    @DisplayName("게시판 삭제 요청하면 정상적으로 작동한다")
    void post_delete_success() throws Exception {

        mockMvc.perform(delete("/api/v1/post/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    @DisplayName("없는 게시판 삭제 요청하면 작동하지 않는다")
    void post_delete_fail() throws Exception {
        doThrow(new PostException(ErrorCode.POST_NOT_FOUND)).when(postService).delete(any(),any());

        mockMvc.perform(delete("/api/v1/post/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    @DisplayName("정상적으로 게시판 목록을 요청하면 성공한다")
    void postList_get_success() throws Exception {
        when(postService.list(any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/post/list")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("비정상적으로 게시판 목록을 요청하면 실패한다")
    void postList_get_fail() throws Exception {
        when(postService.list(any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/post/list")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("좋아요가 정상적으로 작동한다")
    void like_post_success() throws Exception {

        mockMvc.perform(post("/api/v1/post/1/like")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("좋아요가 정상적으로 작동하지 않는다")
    void like_post_fail() throws Exception {

        mockMvc.perform(post("/api/v1/post/1/like")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
