package com.javastriker69.blog.controller;

import com.javastriker69.blog.payload.CommentDto;
import com.javastriker69.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments/new")
    public ResponseEntity<CommentDto> createNewComment(@RequestBody CommentDto commentDto,
                                                       @PathVariable(value = "postId") Long postId){
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments/all")
    public ResponseEntity<List<CommentDto>> getAllCommentByPost(@PathVariable(value = "postId") Long postId){
        return new ResponseEntity<>(commentService.getAllCommentByPost(postId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable(value = "postId")Long postId,
                                                 @PathVariable(value = "commentId")Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable(value = "postId") Long postId,
                                                  @PathVariable(value = "commentId") Long commentId,
                                                  @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }


}
