package com.springboot.blog.payload;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.springboot.blog.entity.Post}
 */

@Data
public class PostDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}