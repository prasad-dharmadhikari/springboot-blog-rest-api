package com.springboot.blog.payload;

import com.springboot.blog.entity.Category;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.springboot.blog.entity.Post}
 */

@Data
public class PostDto implements Serializable {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    //@NotEmpty(message = "Category should not be empty")
    //@NotBlank(message = "Category should not be blank")
    @NotNull(message = "Category should not be null, blank or empty")
    @Min(1)
    private Long categoryId;
}