package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(name = "CRUD REST APIs for 'Comment' Resource")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment REST API",
            description = "Use this API to add comments to specific post, requires post id for the same"
    )
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long id,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Comments by post id REST API",
            description = "Use this API to view comments for the specific post"
    )
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") long postId) {
        return new ResponseEntity<>(commentService.getAllComments(postId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment by post id and comment id REST API",
            description = "Use this API to view specific comment for a specific post"
    )
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByPostIdAndCommentId(@PathVariable("postId") long postId,@PathVariable ("commentId") long commentId) {
        return new ResponseEntity<>(commentService.getCommentByPostIdAndCommentId(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment by post id REST API",
            description = "Use this API to update a specific comment to a specific post"
    )
    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable ("commentId") long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId,commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "Use this API to delete a comment to a specific post"
    )
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("commentId")long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully...",HttpStatus.OK);
    }
}
