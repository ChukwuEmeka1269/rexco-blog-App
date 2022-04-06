package com.javastriker69.blog.repository;

import com.javastriker69.blog.model.Comment;
import com.javastriker69.blog.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.postId = ?1")
    List<Comment> findByPostId(Long postId);


}


