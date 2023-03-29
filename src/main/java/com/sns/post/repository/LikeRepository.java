package com.sns.post.repository;

import com.sns.member.entity.Member;
import com.sns.post.entity.Like;
import com.sns.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberAndPost(Member member, Post post);
    @Query(value = "SELECT COUNT(*) FROM Like l WHERE l.post =:post")
    Integer countByPost(@Param("post") Post post);
}
