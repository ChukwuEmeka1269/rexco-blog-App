package com.javastriker69.blog.service;

import com.javastriker69.blog.payload.PostDto;
import com.javastriker69.blog.payload.PostResponse;


public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    PostDto updatePost(Long postId, PostDto newPostDto);
    void deletePosts();
    void deletePostById(Long postId);
}
