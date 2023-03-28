package com.sns.member.controller;

import com.sns.member.dto.MemberDto;
import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.member.dto.request.MemberLoginRequestDto;
import com.sns.member.dto.response.MemberJoinResponseDto;
import com.sns.member.dto.response.MemberLoginResponseDto;
import com.sns.member.dto.response.Response;
import com.sns.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/join")
    public Response<MemberJoinResponseDto> join(@RequestBody MemberJoinRequestDto requestDto) {
        MemberDto memberDto = MemberDto.from(requestDto);
        MemberDto joinMember = memberService.join(memberDto);

        return Response.success(MemberJoinResponseDto.from(joinMember));
    }

    @PostMapping("/login")
    public Response<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto requestDto) {
        MemberDto memberDto = MemberDto.from(requestDto);
        String token = memberService.login(memberDto);
        return Response.success(new MemberLoginResponseDto(token));
    }
}
