package com.javastriker69.blog.repository;

import com.javastriker69.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    
}
