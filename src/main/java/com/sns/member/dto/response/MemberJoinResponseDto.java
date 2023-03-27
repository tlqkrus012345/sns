package com.sns.member.dto.response;

import com.sns.member.dto.MemberDto;
import com.sns.member.entity.Member;
import com.sns.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinResponseDto {

    private Long id;
    private String memberName;
    private MemberRole memberRole;

    public static MemberJoinResponseDto from(MemberDto memberDto) {
        return new MemberJoinResponseDto(
                memberDto.getId(),
                memberDto.getMemberName(),
                memberDto.getMemberRole()
        );
    }
}
