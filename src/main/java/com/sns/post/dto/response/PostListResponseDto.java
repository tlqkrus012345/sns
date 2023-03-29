package com.sns.post.dto.response;

import com.sns.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String title;
    private String context;
    private MemberDto memberDto;
}
