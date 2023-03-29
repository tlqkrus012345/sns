package com.sns.post.service;

import com.sns.common.exception.ErrorCode;
import com.sns.member.entity.Member;
import com.sns.member.repository.MemberRepository;
import com.sns.post.dto.PostDto;
import com.sns.post.entity.Post;
import com.sns.post.exception.PostException;
import com.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void create(String title, String context, String memberName) {
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new PostException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s is not found", memberName))
        );
        Post post = Post.of(title, context, member);
        postRepository.save(post);
    }
    @Transactional
    public PostDto update(String title, String context, Long postId, String memberName) {
        Member member = findMember(memberName);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostException(ErrorCode.POST_NOT_FOUND, String.format("%s is not found", postId)));

        if (post.getMember() != member) {
            throw new PostException(ErrorCode.INVALID_PERMISSION, String.format("%s is not %s", memberName,postId));
        }

        post.updatePost(title, context);
        return PostDto.fromEntity(postRepository.save(post));
    }
    @Transactional
    public void delete(String memberName, Long postId) {
        Member member = findMember(memberName);

        Post post = findPost(memberName, postId);

        if (post.getMember() != member) {
            throw new PostException(ErrorCode.INVALID_PERMISSION, String.format("%s is not %s", memberName,postId));
        }

        postRepository.delete(post);
    }
    public Page<PostDto> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::fromEntity);
    }
    public Page<PostDto> myList(String memberName, Pageable pageable) {
        Member member = findMember(memberName);

        return postRepository.findAllByMember(member, pageable).map(PostDto::fromEntity);
    }
    private Member findMember(String memberName) {
        return memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new PostException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s is not found", memberName)));
    }
    private Post findPost(String memberName, Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostException(ErrorCode.POST_NOT_FOUND, String.format("%s is not found", postId)));
    }
}
