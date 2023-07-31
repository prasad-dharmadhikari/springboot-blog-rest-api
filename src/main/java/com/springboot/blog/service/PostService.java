package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostByID(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

    PostResponse getAllPostsWithPagination(int pageNo, int pageSize);

    List<PostDto> getPostsBySort(String sortBy,String sortOrder);
}
