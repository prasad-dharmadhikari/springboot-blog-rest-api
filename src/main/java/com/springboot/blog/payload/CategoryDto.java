package com.springboot.blog.payload;

import com.springboot.blog.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {

    private Long id;

    @NotNull(message = "Name should not be null")
    @Size(min = 2, message = "Description should have at least 2 characters")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotNull(message = "Description should not be null")
    @Size(min = 10, message = "Description should have at least 2 characters")
    @NotEmpty(message = "Description should not be empty")
    private String description;
}