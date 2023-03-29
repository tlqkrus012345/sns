package com.sns.post.dto;

import com.sns.member.dto.MemberDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.dto.response.PostResponseDto;
import com.sns.post.entity.Comment;
import com.sns.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String comment;
    private String memberName;
    private Long postId;

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                comment.getMember().getMemberName(),
                comment.getPost().getId()
        );
    }

}
