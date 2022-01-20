package com.javastriker69.blog.controller;

import com.javastriker69.blog.payload.PostDto;
import com.javastriker69.blog.payload.PostResponse;
import com.javastriker69.blog.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createAPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(name = "pageNo", defaultValue = "0", required = false)int pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10", required = false)int pageSize,
                                                   @RequestParam(name = "sortBy", defaultValue = "postId", required = false)String sortBy,
                                                   @RequestParam(name = "sortDir", defaultValue = "asc", required = false)String sortDir){
        return new ResponseEntity<>(postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable (name = "id") Long postId){
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable (name = "id") Long postId, @RequestBody PostDto newPostDto){
        return new ResponseEntity<>(postService.updatePost(postId, newPostDto), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllPost(){
        postService.deletePosts();
        return new ResponseEntity<>(" All post have been deleted successfully", HttpStatus.OK);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<PostDto> editPostTitle(@PathVariable(name = "id") Long postId, @RequestBody String newTitle){
//        return new ResponseEntity<>(postService.updatePostTitle(postId, newTitle), HttpStatus.OK);
//    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<PostDto> editPostDescription(@PathVariable(name = "id") Long postId, @RequestBody String newDescription){
//        return new ResponseEntity<>(postService.updatePostDescription(postId, newDescription), HttpStatus.OK);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<PostDto> editPostContent(@PathVariable(name = "id") Long postId, @RequestBody String newContent){
//        return new ResponseEntity<>(postService.updatePostContent(postId, newContent), HttpStatus.OK);
//    }

}
