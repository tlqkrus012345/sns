package com.sns.member.entity;

import com.sns.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "member_name")
    private String memberName;
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.MEMBER;

    private Member(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }
    public static Member of(String memberName, String password) {
        return new Member(memberName, password);
    }
    public void encodingPassword(String password) {
        this.password = password;
    }
}
