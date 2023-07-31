package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);

    @Query(value = "SELECT * FROM comments WHERE post_id = ?1 AND id = ?2", nativeQuery = true)
    Optional<Comment> findByCommentIdAndPostId(long postId, long id);



}