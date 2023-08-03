package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getAllComments(long postId);

    CommentDto getCommentByPostIdAndCommentId(long postId, long commentId);

    CommentDto updateComment(long postId,long commentId, CommentDto comment);

    void deleteComment(long postId,long commentId);
}
