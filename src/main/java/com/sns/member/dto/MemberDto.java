package com.sns.member.dto;

import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.member.dto.request.MemberLoginRequestDto;
import com.sns.member.entity.Member;
import com.sns.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String memberName;
    private String password;
    private MemberRole memberRole;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private MemberDto(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }

    public static MemberDto from(MemberJoinRequestDto requestDto) {
        return new MemberDto(
                requestDto.getMemberName(),
                requestDto.getPassword()
        );
    }

    public static MemberDto from(MemberLoginRequestDto requestDto) {
        return new MemberDto(
                requestDto.getMemberName(),
                requestDto.getPassword()
        );
    }
    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getMemberName(),
                member.getPassword(),
                member.getRole(),
                member.getRegisteredAt(),
                member.getUpdatedAt(),
                member.getDeletedAt()
        );
    }
    public Member toEntity() {
        return Member.of(memberName, password);
    }

}
