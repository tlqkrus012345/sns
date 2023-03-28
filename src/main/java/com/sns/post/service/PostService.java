package com.sns.post.service;

import com.sns.common.exception.ErrorCode;
import com.sns.member.entity.Member;
import com.sns.member.exception.MemberJoinException;
import com.sns.member.repository.MemberRepository;
import com.sns.post.dto.PostDto;
import com.sns.post.entity.Post;
import com.sns.post.exception.PostCreateException;
import com.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void create(PostDto postDto, String memberName) {
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new PostCreateException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s is not found", memberName))
        );
        Post post = Post.of(postDto.getTitle(), postDto.getContext(), member);
        postRepository.save(post);
    }
}
