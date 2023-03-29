package com.sns.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.member.dto.MemberDto;
import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.member.dto.request.MemberLoginRequestDto;
import com.sns.common.exception.ErrorCode;
import com.sns.member.exception.MemberJoinException;
import com.sns.member.exception.MemberLoginException;
import com.sns.member.service.MemberService;
import com.sns.post.dto.request.CommentRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;
    @Test
    public void 회원가입_정상적으로_동작한다() throws Exception{
        String memberName = "memberName";
        String password = "password";
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto memberDto = MemberDto.from(memberJoinRequestDto);

        when(memberService.join(memberDto)).thenReturn(mock(MemberDto.class));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new MemberJoinRequestDto(memberName, password)))
        ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 동일한_아이디로_회원가입하면_실패한다() throws Exception{
        String memberName = "memberName";
        String password = "password";
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto memberDto = MemberDto.from(memberJoinRequestDto);

        when(memberService.join(memberDto)).thenThrow(new MemberJoinException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequestDto(memberName, password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }
    @Test
    public void 로그인_정상적으로_동작한다() throws Exception{
        String memberName = "memberName";
        String password = "password";
        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto memberDto = MemberDto.from(memberLoginRequestDto);

        when(memberService.login(memberDto)).thenReturn("success_token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequestDto(memberName, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 존재하지_않은_아이디로_로그인하면_실패한다() throws Exception{
        String memberName = "memberName";
        String password = "password";
        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto memberDto = MemberDto.from(memberLoginRequestDto);

        when(memberService.login(memberDto)).thenThrow(new MemberLoginException(ErrorCode.MEMBER_NOT_FOUND, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequestDto(memberName, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 존재하지_않은_패스워드로_로그인하면_실패한다() throws Exception{
        String memberName = "memberName";
        String password = "password";
        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder().memberName(memberName).password(password).build();
        MemberDto memberDto = MemberDto.from(memberLoginRequestDto);

        when(memberService.login(memberDto)).thenThrow(new MemberLoginException(ErrorCode.WRONG_PASSWORD,""));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequestDto(memberName, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser
    @DisplayName("알람이 정상적으로 작동한다")
    void alarm_post_success() throws Exception {
        when(memberService.alarmList(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/members/alarm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new CommentRequestDto("comment")))
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("알람이 정상적으로 작동하지 않는다")
    void alarm_post_fail() throws Exception {
        when(memberService.alarmList(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(post("/api/v1/members/alarm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new CommentRequestDto("comment")))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
