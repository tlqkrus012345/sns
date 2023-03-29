package com.sns.post.service;

import com.sns.common.exception.ErrorCode;
import com.sns.member.entity.Member;
import com.sns.member.repository.MemberRepository;
import com.sns.post.dto.PostDto;
import com.sns.post.dto.request.PostCreateRequestDto;
import com.sns.post.entity.Post;
import com.sns.post.exception.PostException;
import com.sns.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

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
    private Post post;
    private Member member;
    @BeforeEach
    void setUp() {
        String memberName = "memberName";
        String password = "password";
        member = Member.of(memberName, password);
        ReflectionTestUtils.setField(member, "id", 1L);

        String title = "title";
        String context = "context";

        postCreateRequestDto = PostCreateRequestDto.builder()
                .title(title)
                .context(context)
                .build();

        postDto = PostDto.from(postCreateRequestDto);
        post = Post.of(title, context, member);
        ReflectionTestUtils.setField(post, "id", 1L);
    }
    @Test
    @DisplayName("게시판 작성이 성공한다")
    void success_post() {
        String memberName = "memberName";

        when(memberRepository.findByMemberName(memberName)).thenReturn(Optional.of(mock(Member.class)));
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        Assertions.assertDoesNotThrow(() -> postService.create(postDto.getTitle(),postDto.getContext(), memberName));
    }
    @Test
    @DisplayName("존재하지 않은 유저가 게시판 작성하면 실패한다")
    void fail_post() {
        String memberName = "memberName";

        when(memberRepository.findByMemberName(memberName)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        PostException postCreateException = Assertions.assertThrows(PostException.class,
                () -> postService.create(postDto.getTitle(),postDto.getContext(), memberName));
        Assertions.assertEquals(ErrorCode.MEMBER_NOT_FOUND, postCreateException.getErrorCode());
    }

    @Test
    @DisplayName("정상적인 요청으로 게시판 수정이 성공한다")
    void post_update_success() {
        Long postId = 1L;
        String memberName = "memberName";

        when(memberRepository.findByMemberName(memberName)).thenReturn(Optional.of(member));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Assertions.assertDoesNotThrow(() -> postService.update(post.getTitle(),post.getContext(), postId,memberName));
    }

    @Test
    @DisplayName("게시판 요청을 하면 정상적으로 목록을 반환한다")
    void post_list_success() {
        Pageable pageable = mock(Pageable.class);
        when(postRepository.findAll(pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(() -> postService.list(pageable));
    }
    @Test
    @DisplayName("나의 게시판 요청을 하면 정상적으로 목록을 반환한다")
    void post_myList_success() {
        Pageable pageable = mock(Pageable.class);
        Member member1 = mock(Member.class);
        when(postRepository.findAllByMember(any(),pageable)).thenReturn(Page.empty());
        when(memberRepository.findByMemberName(any())).thenReturn(Optional.of(member1));
        Assertions.assertDoesNotThrow(() -> postService.myList("",pageable));
    }
}
