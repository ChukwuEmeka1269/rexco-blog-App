package com.javastriker69.blog.service;

import com.javastriker69.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPost();
    PostDto getPostById(Long postId);
    PostDto updatePost(Long postId, PostDto newPostDto);
    void deletePosts();
    void deletePostById(Long postId);
//    PostDto updatePostTitle(Long postId, String newPostDto);
//    PostDto updatePostDescription(Long postId, String newDescription);
//    PostDto updatePostContent(Long postId, String newDescription);


}
