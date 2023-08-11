package com.springboot.blog.service.impl;

import com.springboot.blog.configuration.ModelMapperConfiguration;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final ModelMapper modelMapper;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public PostServiceImpl() {
        this.modelMapper = new ModelMapperConfiguration().getModelMapper();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        final var category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId()));
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByID(long id) {
        return modelMapper.map(
                postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id))
                , PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(long id) {
        postRepository.delete
                (postRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id))
                );
    }

    @Override
    public PostResponse getAllPostsWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var postsOnPage = postRepository.findAll(pageable);
        List<PostDto> content = postsOnPage
                .getContent()
                .stream()
                .map((post -> modelMapper.map(post, PostDto.class)))
                .toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setTotalPages(postsOnPage.getTotalPages());
        postResponse.setTotalElements(postsOnPage.getTotalElements());
        postResponse.setPageNo(postsOnPage.getNumber());
        postResponse.setPageSize(postsOnPage.getSize());
        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostsBySort(String sortBy, String sortOrder) {
        Sort.Direction direction;
        if (sortOrder.equalsIgnoreCase("ASC"))
            direction = Sort.Direction.ASC;
        else if (sortOrder.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;
        else
            throw new IllegalArgumentException("Please enter valid sort direction");
        return postRepository
                .findAll(Sort.by(direction, sortBy))
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .toList();
    }
}
