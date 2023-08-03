package com.springboot.blog.service.impl;

import com.springboot.blog.configuration.ModelMapperConfiguration;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = new ModelMapperConfiguration().getModelMapper();
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Id", postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }


    @Override
    public List<CommentDto> getAllComments(long postId) {
        return commentRepository
                .findByPostId(postId)
                .stream()
                .map((comment) -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());


    }

    @Override
    public CommentDto getCommentByPostIdAndCommentId(long postId, long commentId) {
        Comment comment = checkIfCommentOrPostExists(postId, commentId);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        Comment comment = checkIfCommentOrPostExists(postId, commentId);
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = checkIfCommentOrPostExists(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment checkIfCommentOrPostExists(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        else {
            return comment;
        }
    }
}
