package com.sns.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_name")
    private String memberName;
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.MEMBER;
    @Column(name = "registered_at")
    private Timestamp registeredAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registerAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updateAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    private Member(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }
    public static Member of(String memberName, String password) {
        return new Member(memberName,password);
    }
}
