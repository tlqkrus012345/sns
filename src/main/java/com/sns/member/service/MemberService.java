package com.sns.member.service;

import com.sns.member.dto.MemberDto;
import com.sns.member.entity.Member;
import com.sns.member.exception.ErrorCode;
import com.sns.member.exception.MemberJoinException;
import com.sns.member.exception.MemberLoginException;
import com.sns.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    @Transactional
    public MemberDto join(MemberDto memberDto) {

        Optional<Member> findMember = memberRepository.findByMemberName(memberDto.getMemberName());
        if (findMember.isPresent()) {
            throw new MemberJoinException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",memberDto.getMemberName()));
        }
        Member member = memberDto.toEntity();

        String encodedPassword = encoder.encode(memberDto.getPassword());
        member.encodingPassword(encodedPassword);

        return memberDto.from(member);
    }
    // 유저를 파악하기 위해 암호화된 문자열을 통해서 인증한다.
    public String login(MemberDto memberDto) {

        Member member = memberRepository.findByMemberName(memberDto.getMemberName()).orElseThrow(
                () -> new MemberLoginException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not found", memberDto.getMemberName())));

        if (!member.getPassword().equals(memberDto.getPassword())) {
            throw new MemberLoginException(ErrorCode.WRONG_PASSWORD, String.format("%s is wrong", memberDto.getPassword()));
        }

        return "";
    }
}
