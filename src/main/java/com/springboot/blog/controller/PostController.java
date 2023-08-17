package com.springboot.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for 'Post' Resource")
public class PostController {
    public final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Provide ADMIN access to Create Post, Update Post and Delete Post
    @Operation(
            summary = "Create Post REST API",
            description = "Consume this API to create posts, requires ADMIN access and needs to be authenticated first"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get All Posts REST API",
            description = "Consume this API to view all posts"
    )
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post by id REST API",
            description = "Consume this API to view the particular post by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostByID(id));
    }


    @Operation(
            summary = "Update Post REST API",
            description = "Consume this API to update a post by id, requires ADMIN access and needs to be authenticated first"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Consume this API to delete a post by id, requires ADMIN access and needs to be authenticated"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deletePost(@PathVariable(name = "id") long id) throws JsonProcessingException {
        postService.deletePost(id);
        ObjectMapper objectMapper = new ObjectMapper();
        var objectNode = objectMapper.createObjectNode();
        objectNode.put("message", "Post deleted successfully...");
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @Operation(
            summary = "View posts on particular page REST API",
            description = "Consume this API to view the posts on particular page,you can also provide no of posts per page"
    )
    @GetMapping("/pagination")
    public ResponseEntity<PostResponse> getPostByPagination
            (@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
            ) {
        return ResponseEntity.ok(postService.getAllPostsWithPagination(pageNo, pageSize));
    }

    @Operation(
            summary = "Sort Posts REST API",
            description = "Consume this API to sort the posts, in ascending or descending order and by particular field"
    )
    @GetMapping("/sort")
    public ResponseEntity<List<PostDto>> getPostsBySort
            (@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)String sortBy,
             @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortOrder
            ) {
        return ResponseEntity.ok(postService.getPostsBySort(sortBy,sortOrder));
    }

    @Operation(
            summary = "Get Post by category REST API",
            description = "Consume this API to view the posts of a specific category"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }
}
