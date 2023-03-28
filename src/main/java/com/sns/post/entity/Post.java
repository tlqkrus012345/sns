package com.sns.post.entity;

import com.sns.common.BaseTimeEntity;
import com.sns.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String context;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Post(String title, String context, Member member) {
        this.title = title;
        this.context = context;
        this.member = member;
    }
    public static Post of(String title, String context, Member member) {
        return new Post(title, context, member);
    }
}
