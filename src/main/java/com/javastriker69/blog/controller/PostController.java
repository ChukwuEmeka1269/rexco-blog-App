package com.javastriker69.blog.controller;

import com.javastriker69.blog.payload.PostDto;
import com.javastriker69.blog.payload.PostResponse;
import com.javastriker69.blog.service.PostService;
import com.javastriker69.blog.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createAPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(name = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
                                                   @RequestParam(name = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false)String sortBy,
                                                   @RequestParam(name = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false)String sortDir){
        return new ResponseEntity<>(postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable (value = "id") Long postId){
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> editPost(@PathVariable (value = "id") Long postId, @Valid @RequestBody PostDto newPostDto){
        return new ResponseEntity<>(postService.updatePost(postId, newPostDto), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(value = "id") Long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAllPost(){
        postService.deletePosts();
        return new ResponseEntity<>(" All post have been deleted successfully", HttpStatus.OK);
    }

}
