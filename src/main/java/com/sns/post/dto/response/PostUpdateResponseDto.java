package com.sns.post.dto.response;

import com.sns.member.dto.MemberDto;
import com.sns.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PostUpdateResponseDto {

    private Long id;
    private String title;
    private String context;
    private MemberDto memberDto;
}
