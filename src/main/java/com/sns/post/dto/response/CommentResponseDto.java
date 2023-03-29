package com.sns.post.dto.response;

import com.sns.member.dto.MemberDto;
import com.sns.post.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String memberName;
    private Long postId;
    public static CommentResponseDto from(CommentDto commentDto) {
        return new CommentResponseDto(
                commentDto.getId(),
                commentDto.getComment(),
                commentDto.getMemberName(),
                commentDto.getId()
        );
    }
}
