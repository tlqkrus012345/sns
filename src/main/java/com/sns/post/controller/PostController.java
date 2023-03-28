package com.sns.post.controller;

import com.sns.member.dto.response.Response;
import com.sns.post.dto.PostDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequestDto requestDto, Authentication authentication) {
        postService.create(PostDto.from(requestDto), authentication.getName());
        return Response.success();
    }
}
