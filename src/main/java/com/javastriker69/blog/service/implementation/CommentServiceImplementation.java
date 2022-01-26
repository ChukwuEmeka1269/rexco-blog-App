package com.javastriker69.blog.service.implementation;

import com.javastriker69.blog.exception.APIException;
import com.javastriker69.blog.exception.ResourceNotFoundException;
import com.javastriker69.blog.model.Comment;
import com.javastriker69.blog.model.Post;
import com.javastriker69.blog.payload.CommentDto;
import com.javastriker69.blog.repository.CommentRepository;
import com.javastriker69.blog.repository.PostRepository;
import com.javastriker69.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private ModelMapper mapper;

    public CommentServiceImplementation(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Comment mappedComment = mapDtoToComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        mappedComment.setPost(post);
        Comment savedComment = commentRepository.save(mappedComment);
        return mapCommentToDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllCommentByPost(Long postId) {
        List<Comment> commentsByPostId = commentRepository.findByPostId(postId);

        return commentsByPostId.stream().map(this::mapCommentToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment = findComment(postId, commentId);

        return mapCommentToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto newComment) {
        Comment comment = findComment(postId, commentId);

        comment.setName(newComment.getName());
        comment.setEmail(newComment.getEmail());
        comment.setBody(newComment.getBody());

        commentRepository.save(comment);

        return mapCommentToDto(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = findComment(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment findComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getPostId().equals(post.getPostId())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Post with "+ postId + " does not contain comment with id "+ commentId);
        }

        return comment;
    }


    private CommentDto mapCommentToDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapDtoToComment(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }
}
