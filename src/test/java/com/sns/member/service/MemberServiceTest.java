package com.sns.member.service;

import com.sns.member.dto.MemberDto;
import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.member.dto.request.MemberLoginRequestDto;
import com.sns.member.entity.Member;
import com.sns.member.exception.MemberJoinException;
import com.sns.member.exception.MemberLoginException;
import com.sns.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    private MemberDto memberDto;
    private Member member;
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberDto = MemberDto.from(MemberJoinRequestDto.builder()
                .memberName("memberName")
                .password("password")
                .build());
        member = memberDto.toEntity();
        ReflectionTestUtils.setField(member, "id", 1L);
    }
    @Test
    void 회원가입_서비스_정상적으로_동작한다() {
        String memberName = "newMemberName";
        String password = "password";
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto memberDto = MemberDto.from(memberJoinRequestDto);

        when(memberRepository.findByMemberName(memberDto.getMemberName())).thenReturn(Optional.empty());
        when(memberRepository.save(any())).thenReturn(Optional.of(Member.class));

        Assertions.assertDoesNotThrow(() -> memberService.join(memberDto));
    }

    @Test
    void 회원가입_서비스_정상적으로_동작하지_않는다_동일한_memberName_존재해서() {
        String memberName = "memberName";
        String password = "password";
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto newMemberDto = MemberDto.from(memberJoinRequestDto);


        when(memberRepository.findByMemberName(newMemberDto.getMemberName())).thenReturn(Optional.of(member));
        when(memberRepository.save(any())).thenReturn(mock(Member.class));

        Assertions.assertThrows(MemberJoinException.class,() -> memberService.join(newMemberDto));
    }

    @Test
    void 로그인_서비스_정상적으로_동작한다() {

        when(memberRepository.findByMemberName(memberDto.getMemberName())).thenReturn(Optional.of(member));

        Assertions.assertDoesNotThrow(() -> memberService.login(memberDto));
    }

    @Test
    void 로그인_서비스_정상적으로_동작하지_않는다_memberName_존재하지_않아서() {

        when(memberRepository.findByMemberName(memberDto.getMemberName())).thenReturn(Optional.empty());

        Assertions.assertThrows(MemberLoginException.class, () -> memberService.login(memberDto));
    }

    @Test
    void 로그인_서비스_정상적으로_동작하지_않는다_password_존재하지_않아서() {
        String memberName = "memberName";
        String wrongPassword = "wrongPassword";
        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder().memberName(memberName).password(wrongPassword).build();
        MemberDto memberDto = MemberDto.from(memberLoginRequestDto);

        when(memberRepository.findByMemberName(memberDto.getMemberName())).thenReturn(Optional.of(member));

        Assertions.assertThrows(MemberLoginException.class, () -> memberService.login(memberDto));
    }
}
