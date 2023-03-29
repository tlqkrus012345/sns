package com.sns.post.controller;

import com.sns.member.dto.response.Response;
import com.sns.post.dto.PostDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.dto.request.PostUpdateRequestDto;
import com.sns.post.dto.response.PostListResponseDto;
import com.sns.post.dto.response.PostResponseDto;
import com.sns.post.dto.response.PostUpdateResponseDto;
import com.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    @PostMapping("/create")
    public Response<Void> create(@RequestBody PostCreateRequestDto requestDto, Authentication authentication) {
        postService.create(requestDto.getTitle(),requestDto.getContext(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/update/{postId}")
    public Response<PostResponseDto> update(@PathVariable Long postId, @RequestBody PostUpdateRequestDto requestDto, Authentication authentication) {
        PostDto postDto = postService.update(requestDto.getTitle(), requestDto.getContext(), postId, authentication.getName());
        return Response.success(PostDto.from(postDto));
    }

    @DeleteMapping("/delete/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return Response.success();
    }
    @GetMapping("/list")
    public Response<Page<PostResponseDto>> list(Pageable pageable, Authentication authentication) {
        return Response.success(postService.list(pageable).map(PostDto::from));
    }
    @GetMapping("/my")
    public Response<Page<PostResponseDto>> myList(Pageable pageable, Authentication authentication) {
        return Response.success(postService.myList(authentication.getName(),pageable).map(PostDto::from));
    }
    @PostMapping("/{postId}/like")
    public Response<Void> like(@PathVariable Long postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        return Response.success();
    }
    @GetMapping("/{postId}/like")
    public Response<Integer> likeCount(@PathVariable Long postId, Authentication authentication) {
        return Response.success(postService.likeCount(postId));
    }
}
