package com.learning.blogplatformapi.service;

import com.learning.blogplatformapi.model.Post;
import com.learning.blogplatformapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Transactional
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public List<Post> findByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    @Transactional
    public Post updatePost(long id, Post updatedPost){
        return postRepository.findById(id).map(post -> {
            post.setContent(updatedPost.getContent());
            post.setTitle(updatedPost.getTitle());
            post.setCreatedAt(ZonedDateTime.now());
            return postRepository.save(post);
        }).orElseThrow(()->new RuntimeException("Post not found"));
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
