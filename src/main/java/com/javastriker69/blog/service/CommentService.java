package com.javastriker69.blog.service;

import com.javastriker69.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto comment, Long postId);
    List<CommentDto> getAllCommentByPost(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto newComment);

    void deleteComment(Long postId, Long commentId);
}
