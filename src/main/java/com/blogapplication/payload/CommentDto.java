package com.blogapplication.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "CommentDto"
)
public class CommentDto {
    @Schema(description = "comment Id")
    private long id;
    @NotEmpty(message = "name should not be empty")
    @Schema(description = "comment name")
    private String name;
    @NotEmpty(message = "email should not be null")
    @Email(message = "invalid email id")
    @Schema(description = "email of the user")
    private String email;
    @NotEmpty(message = "body can not be null")
    @Size(min = 10,message = "body should be greater than 10 characters")
    @Schema(description = "body of the comment")
    private String body;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
