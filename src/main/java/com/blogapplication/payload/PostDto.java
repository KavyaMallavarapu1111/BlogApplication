package com.blogapplication.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
@Schema(
        description = "PostDto "
)
public class PostDto {

    @Schema(
            description = "Blog Post Id"
    )
    private Long id;
    @NotEmpty(message = "Should not be null")
    @Size(min = 2, message = "tiitle length should be greater than 2")
    @Schema(
            description = "Blog Post Title"
    )
    private String title;
    @NotEmpty(message = "Should not be null")
    @Schema(
            description = "Blog Post Description"
    )
    @Size(min = 10, message = "description length should be greater than 10")
    private String description;
    @NotEmpty(message = "Should not be null")
    @Schema(
            description = "Blog Post content"
    )
    private String content;
    @Schema(
            description = "Blog Post Comments"
    )
    private Set<CommentDto> comments;
    @Schema(
            description = "Blog Post CategoryId"
    )
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
