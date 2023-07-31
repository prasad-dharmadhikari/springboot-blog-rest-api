package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    private String name;

    @NotNull(message = "Email should not be null")
    @Email(message = "Enter valid email id")
    @NotEmpty(message = "Email id should not be empty")
    private String email;

    @NotNull(message = "Body should not be null")
    private String body;
}