package com.javastriker69.blog.service.implementation;

import com.javastriker69.blog.exception.ResourceNotFoundException;
import com.javastriker69.blog.model.Post;
import com.javastriker69.blog.payload.PostDto;
import com.javastriker69.blog.payload.PostResponse;
import com.javastriker69.blog.repository.PostRepository;
import com.javastriker69.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {
    private ModelMapper mapper;
    private final PostRepository postRepository;

    public PostServiceImplementation(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post convertedPost = mapDtoToPost(postDto);

        Post savedPost = postRepository.save(convertedPost);

        return mapPostToDto(savedPost);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postPages = postRepository.findAll(pageable);

        List<Post> pagesContent = postPages.getContent();

        List<PostDto> postDtos = pagesContent.stream().map(this::mapPostToDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNo(postPages.getNumber());
        postResponse.setPageSize(postPages.getSize());
        postResponse.setTotalElement(postPages.getTotalElements());
        postResponse.setTotalPages(postPages.getTotalPages());
        postResponse.setLast(postPages.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        return  mapPostToDto(post);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
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
                () -> new ResourceNotFoundException("Post", "id", postId)
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
        return mapper.map(postDto, Post.class);
    }

    private PostDto mapPostToDto(Post post) {

        return mapper.map(post, PostDto.class);
    }


}
