package com.sns.post.entity;

import com.sns.common.BaseTimeEntity;
import com.sns.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "likes")
@NoArgsConstructor
@Entity
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Like(Member member, Post post) {
        this.member = member;
        this.post =post;
    }
    public static Like of(Member member, Post post) {
        return new Like(member, post);
    }
}
