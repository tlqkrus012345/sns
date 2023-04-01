package com.sns.member.service;

import com.sns.member.dto.AlarmDto;
import com.sns.member.dto.MemberDto;
import com.sns.member.entity.Member;
import com.sns.common.exception.ErrorCode;
import com.sns.member.exception.MemberJoinException;
import com.sns.member.exception.MemberLoginException;
import com.sns.member.repository.AlarmRepository;
import com.sns.member.repository.MemberCacheRepository;
import com.sns.member.repository.MemberRepository;
import com.sns.common.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;
    private final BCryptPasswordEncoder encoder;
    private final MemberCacheRepository memberCacheRepository;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public MemberDto join(MemberDto memberDto) {

        Optional<Member> findMember = memberRepository.findByMemberName(memberDto.getMemberName());
        if (findMember.isPresent()) {
            throw new MemberJoinException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",memberDto.getMemberName()));
        }
        Member member = memberDto.toEntity();

        String encodedPassword = encoder.encode(memberDto.getPassword());
        member.encodingPassword(encodedPassword);

        return memberDto.from(memberRepository.save(member));
    }
    // 유저를 파악하기 위해 암호화된 문자열을 통해서 인증한다.
    public String login(MemberDto memberDto) {

        MemberDto findMemberDto = findMemberByMemberName(memberDto.getMemberName());
        memberCacheRepository.setMemberDto(findMemberDto);

        if (!encoder.matches(memberDto.getPassword(), findMemberDto.getPassword())) {
            throw new MemberLoginException(ErrorCode.WRONG_PASSWORD, String.format("%s is wrong", memberDto.getPassword()));
        }

        String token = JwtTokenUtils.generateToken(memberDto.getMemberName(), secretKey, expiredTimeMs);
        return token;
    }
    public MemberDto findMemberByMemberName(String memberName) {
        return memberCacheRepository.getMemberDto(memberName).orElseGet(() ->
            memberRepository.findByMemberName(memberName).map(MemberDto :: fromEntity).orElseThrow(
                () -> new MemberJoinException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not found",memberName))
         ));
    }
    public Page<AlarmDto> alarmList(String memberName, Pageable pageable) {
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new MemberJoinException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not found", memberName)));

        return alarmRepository.findAllByMember(member, pageable).map(AlarmDto::from);

    }
}
