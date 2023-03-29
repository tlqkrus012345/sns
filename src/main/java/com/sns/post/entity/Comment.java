package com.sns.post.entity;

import com.sns.common.BaseTimeEntity;
import com.sns.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Table(indexes = {
        @Index(name ="post_id_idx", columnList = "post_id")
})
@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Comment(Post post, Member member, String comment) {
        this.post = post;
        this.member = member;
        this.comment = comment;
    }
    public static Comment of(Post post, Member member, String comment) {
        return new Comment(post,member,comment);
    }
}