package com.sns.post.repository;

import com.sns.post.entity.Comment;
import com.sns.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPost(Post post, Pageable pageable);
    @Transactional
    @Modifying
    @Query("DELETE FROM Comment o WHERE o.post = :post")
    void deleteAllByPost(@Param("post") Post post);
}
