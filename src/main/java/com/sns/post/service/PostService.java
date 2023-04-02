package com.sns.post.service;

import com.sns.common.exception.ErrorCode;
import com.sns.event.dto.AlarmEvent;
import com.sns.event.producer.AlarmProducer;
import com.sns.member.entity.alarm.Alarm;
import com.sns.member.entity.alarm.AlarmArguments;
import com.sns.member.entity.alarm.AlarmType;
import com.sns.member.entity.Member;
import com.sns.member.repository.AlarmRepository;
import com.sns.member.repository.MemberRepository;
import com.sns.post.dto.PostDto;
import com.sns.post.entity.Like;
import com.sns.post.entity.Post;
import com.sns.post.exception.LikeException;
import com.sns.post.exception.PostException;
import com.sns.post.repository.CommentRepository;
import com.sns.post.repository.LikeRepository;
import com.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final AlarmRepository alarmRepository;
    private final CommentRepository commentRepository;
    private final AlarmProducer alarmProducer;
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
        Member member = existMember(memberName);

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
        Member member = existMember(memberName);

        Post post = existPost(postId);

        if (post.getMember() != member) {
            throw new PostException(ErrorCode.INVALID_PERMISSION, String.format("%s is not %s", memberName,postId));
        }
        likeRepository.deleteAllByPost(post);
        commentRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }
    public Page<PostDto> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::fromEntity);
    }
    public Page<PostDto> myList(String memberName, Pageable pageable) {
        Member member = existMember(memberName);

        return postRepository.findAllByMember(member, pageable).map(PostDto::fromEntity);
    }
    @Transactional
    public void like(Long postId, String memberName) {
        Member member = existMember(memberName);
        Post post = existPost(postId);

        Optional<Like> memberAndPost = likeRepository.findByMemberAndPost(member, post);
        if (memberAndPost.isPresent()) {
            throw new LikeException(ErrorCode.ALREADY_LIKE,String.format("%s already like postId : %s,", memberName, postId));
        }

        likeRepository.save(Like.of(member,post));

        alarmProducer.send(new AlarmEvent(post.getMember().getId(), AlarmType.NEW_LIKE_ON_POST, new AlarmArguments(member.getId(), post.getId())));
    }
    public Integer likeCount(Long postId) {
        Post post = existPost(postId);

        return likeRepository.countByPost(post);
    }
    Member existMember(String memberName) {
        return memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new PostException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s is not found", memberName)));
    }
    Post existPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostException(ErrorCode.POST_NOT_FOUND, String.format("%s is not found", postId)));
    }
}
