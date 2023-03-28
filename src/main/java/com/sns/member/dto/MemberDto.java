package com.sns.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sns.member.dto.request.MemberJoinRequestDto;
import com.sns.member.dto.request.MemberLoginRequestDto;
import com.sns.member.entity.Member;
import com.sns.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto implements UserDetails {
    private Long id;
    private String memberName;
    private String password;
    private MemberRole memberRole;

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
                member.getRole()

        );
    }
    public Member toEntity() {
        return Member.of(memberName, password);
    }

    public Member toEntity(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        return Member.of(memberDto.getMemberName(), passwordEncoder.encode(memberDto.getPassword()));
    }
    public static MemberDto fromEntity(Member member) {
        return new MemberDto(
                member.getId(),
                member.getMemberName(),
                member.getPassword(),
                member.getRole()
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getMemberRole().toString()));
    }
    @Override
    public String getUsername() {
        return this.memberName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
