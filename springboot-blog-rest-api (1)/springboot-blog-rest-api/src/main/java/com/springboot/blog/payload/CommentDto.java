package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CommentDto {

    private Long id;
    @NotEmpty(message = "name should not be empty")
    private String name;
    @NotEmpty(message = "name should not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min=10,message = "comment body must have 10 characters")
    private String body;


}
