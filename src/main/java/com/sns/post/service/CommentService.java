package com.sns.post.service;

import com.sns.event.dto.AlarmEvent;
import com.sns.event.producer.AlarmProducer;
import com.sns.member.entity.alarm.Alarm;
import com.sns.member.entity.alarm.AlarmArguments;
import com.sns.member.entity.alarm.AlarmType;
import com.sns.member.entity.Member;
import com.sns.member.repository.AlarmRepository;
import com.sns.member.service.AlarmService;
import com.sns.post.dto.CommentDto;
import com.sns.post.entity.Comment;
import com.sns.post.entity.Post;
import com.sns.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;
    private final AlarmProducer alarmProducer;
    @Transactional
    public void comment(Long postId, String comment, String memberName) {
        Member member = postService.existMember(memberName);
        Post post = postService.existPost(postId);

        commentRepository.save(Comment.of(post, member, comment));

        //alarmRepository.save(Alarm.of(post.getMember(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArguments(member.getId(),post.getId())));
        alarmProducer.send(new AlarmEvent(post.getMember().getId(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArguments(member.getId(),post.getId())));
    }
    public Page<CommentDto> list(Long postId, Pageable pageable) {
        Post post = postService.existPost(postId);
        return commentRepository.findAllByPost(post, pageable).map(CommentDto::from);
    }
}
