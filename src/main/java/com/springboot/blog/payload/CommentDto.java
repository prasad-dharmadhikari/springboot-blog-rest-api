package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Comment}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto implements Serializable {

    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    @Size(min = 2,message = "Name should be at least of 2 characters")
    private String name;

    @NotNull(message = "Email should not be null")
    @Email(message = "Enter valid email id")
    @NotEmpty(message = "Email id should not be empty")
    private String email;

    @NotEmpty(message = "Body should not be null")
    @Size(min = 10, message = "Body should have at least 10 characters")
    private String body;
}