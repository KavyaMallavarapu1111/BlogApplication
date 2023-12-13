package com.blogapplication.controllers;

import com.blogapplication.payload.PostDto;
import com.blogapplication.payload.PostResponse;
import com.blogapplication.service.PostService;
import com.blogapplication.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD APIS of Post Resource"
)
public class PostController {

    private PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }


    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("getbyid/{id}")
    @Operation(
            summary = "Get Post REST API",
            description = "Get Post REST API is used to get post from database by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id)
    {
        return new ResponseEntity<>(postService.getPost(id),HttpStatus.OK);
    }

    @GetMapping("/getallposts")
    @Operation(
            summary = "Get Posts REST API",
            description = "Get Post REST API is used to get all the posts from database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<PostResponse> getPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) int pageNo,
                                                 @RequestParam(value = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE ,required = false) int pageSize ,
                                                 @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                 @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
    {
        return new ResponseEntity<>(postService.getPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("updatebyid/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update post in database by id"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletebyid/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete post from database by id"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id)
    {
        postService.deletePost(id);
        return ResponseEntity.ok("Deleted successfully...");
    }

    @GetMapping("/getpostsbycategoryid/{id}")
    @Operation(
            summary = "Get Posts By Category REST API",
            description = "Get Posts By Category REST API is used to get posts from database by category id"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<List<PostDto>> findByCategoryId(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(postService.findByCategoryId(id));
    }
}
