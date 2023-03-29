package com.sns.member.dto.response;

import com.sns.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String memberName;

    public static MemberResponseDto from(MemberDto memberDto) {
        return new MemberResponseDto(
                memberDto.getId(),
                memberDto.getMemberName()
        );
    }
}
