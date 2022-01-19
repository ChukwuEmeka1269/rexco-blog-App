package com.javastriker69.blog.service.implementation;

import com.javastriker69.blog.model.Post;
import com.javastriker69.blog.payload.PostDto;
import com.javastriker69.blog.repository.PostRepository;
import com.javastriker69.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImplementation implements PostService {
    private PostRepository postRepository;

    public PostServiceImplementation(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post convertedPost = new Post();
        convertedPost.setPostId(postDto.getId());
        convertedPost.setTitle(postDto.getTitle());
        convertedPost.setDescription(postDto.getDescription());
        convertedPost.setContent(postDto.getContent());

        Post savedPost = postRepository.save(convertedPost);

        PostDto responseDto = new PostDto();
        responseDto.setId(savedPost.getPostId());
        responseDto.setTitle(savedPost.getTitle());
        responseDto.setDescription(savedPost.getDescription());
        responseDto.setContent(savedPost.getContent());

        return responseDto;
    }
}
