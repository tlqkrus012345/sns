package com.sns.post.dto;

import com.sns.member.dto.MemberDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private MemberDto memberDto;
    private String title;
    private String context;
    private PostDto(String title, String context) {
        this.title = title;
        this.context = context;
    }
    public static PostDto from(PostCreateRequestDto requestDto) {
        return new PostDto(
                requestDto.getTitle(),
                requestDto.getContext()
        );
    }
}
