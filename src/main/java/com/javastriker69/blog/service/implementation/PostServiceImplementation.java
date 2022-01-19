package com.javastriker69.blog.service.implementation;

import com.javastriker69.blog.exception.ResourceNotFoundException;
import com.javastriker69.blog.model.Post;
import com.javastriker69.blog.payload.PostDto;
import com.javastriker69.blog.repository.PostRepository;
import com.javastriker69.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {
    private PostRepository postRepository;

    public PostServiceImplementation(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post convertedPost = mapDtoToPost(postDto);

        Post savedPost = postRepository.save(convertedPost);

        return mapPostToDto(savedPost);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> AllPostsInRepository = postRepository.findAll();
        return AllPostsInRepository.stream().map(this::mapPostToDto).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        return  mapPostToDto(post);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        PostDto postDto = mapPostToDto(post);
        postDto.setId(postId);
        postDto.setTitle(newPostDto.getTitle());
        postDto.setDescription(newPostDto.getDescription());
        postDto.setContent(newPostDto.getContent());

        postRepository.save(mapDtoToPost(postDto));
        return postDto;
    }

    @Override
    public void deletePosts() {
         postRepository.deleteAll();
    }

    @Override
    public void deletePostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        postRepository.delete(post);
    }


//    @Override
//    public PostDto updatePostTitle(Long postId, String newTitle) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResourceNotFoundException("Post", "postId", postId)
//        );
//
//        if(post.getTitle().equals(newTitle)){
//            throw new IllegalStateException("The title is still the same. Are you sure you want to change the title?");
//        }
//        else{
//            post.setTitle(newTitle);
//            postRepository.save(post);
//            return mapPostToDto(post);
//        }
//
//    }

//    @Override
//    public PostDto updatePostDescription(Long postId, String newDescription) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResourceNotFoundException("Post", "postId", postId)
//        );
//
//        if(post.getTitle().equals(newDescription)){
//            throw new IllegalStateException("No change has been made.");
//        }
//        else{
//            post.setDescription(newDescription);
//            postRepository.save(post);
//            return mapPostToDto(post);
//        }
//    }
//
//    @Override
//    public PostDto updatePostContent(Long postId, String newContent) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResourceNotFoundException("Post", "postId", postId)
//        );
//
//        if(post.getTitle().equals(newContent)){
//            throw new IllegalStateException("No change has been made.");
//        }
//        else{
//            post.setContent(newContent);
//            postRepository.save(post);
//            return mapPostToDto(post);
//        }
//    }

    private Post mapDtoToPost(PostDto postDto) {
        Post convertedPost = new Post();
        convertedPost.setPostId(postDto.getId());
        convertedPost.setTitle(postDto.getTitle());
        convertedPost.setDescription(postDto.getDescription());
        convertedPost.setContent(postDto.getContent());
        return convertedPost;
    }

    private PostDto mapPostToDto(Post post) {
        PostDto responseDto = new PostDto();
        responseDto.setId(post.getPostId());
        responseDto.setTitle(post.getTitle());
        responseDto.setDescription(post.getDescription());
        responseDto.setContent(post.getContent());
        return responseDto;
    }


}
