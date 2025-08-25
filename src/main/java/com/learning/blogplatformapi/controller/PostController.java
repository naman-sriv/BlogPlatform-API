package com.learning.blogplatformapi.controller;

import com.learning.blogplatformapi.model.Post;
import com.learning.blogplatformapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false) Long authorId) {
        if(authorId!=null){
            List<Post> posts = postService.findByAuthorId(authorId);
            return new ResponseEntity<>(new PageImpl<>(posts),HttpStatus.OK);
        }else{
            Page<Post> posts = postService.findAll(PageRequest.of(page,size));
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        Optional<Post> postOptional = postService.findById(id);
        return postOptional.map(post -> new ResponseEntity<>(post, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("isAuthenticated() and @postService.findById(#id).get().author.id==authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost){
        try{
            Post post = postService.updatePost(id, updatedPost);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated() and @postService.findById(#id).get().author.id==authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try{
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
