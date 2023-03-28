package com.sns.post.service;

import com.sns.common.exception.ErrorCode;
import com.sns.member.entity.Member;
import com.sns.member.repository.MemberRepository;
import com.sns.post.dto.PostDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.entity.Post;
import com.sns.post.exception.PostCreateException;
import com.sns.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;
    private PostCreateRequestDto postCreateRequestDto;
    private PostDto postDto;
    @BeforeEach
    void setUp() {

        String title = "title";
        String context = "context";

        postCreateRequestDto = PostCreateRequestDto.builder()
                .title(title)
                .context(context)
                .build();

        postDto = PostDto.from(postCreateRequestDto);
    }
    @Test
    @DisplayName("게시판 작성이 성공한다")
    void success_post() {
        String memberName = "memberName";

        when(memberRepository.findByMemberName(memberName)).thenReturn(Optional.of(mock(Member.class)));
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        Assertions.assertDoesNotThrow(() -> postService.create(postDto, memberName));
    }
    @Test
    @DisplayName("존재하지 않은 유저가 게시판 작성하면 실패한다")
    void fail_post() {
        String memberName = "memberName";

        when(memberRepository.findByMemberName(memberName)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        PostCreateException postCreateException = Assertions.assertThrows(PostCreateException.class,
                () -> postService.create(postDto, memberName));
        Assertions.assertEquals(ErrorCode.MEMBER_NOT_FOUND, postCreateException.getErrorCode());
    }
}
