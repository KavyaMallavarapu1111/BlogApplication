package com.blogapplication.controllers;


import com.blogapplication.payload.CommentDto;
import com.blogapplication.service.CommentService;
import com.blogapplication.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(
        name = "Comment APIs"
)
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Create  Comment REST API" ,
            description = "Create Comment REST API is used to create comment in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("id") long id)
    {
        return new ResponseEntity<>(commentService.createComment(id,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/commentbypostid/{id}")
    @Operation(
            summary = "Get Comment By Post Id REST API" ,
            description = "Get Comment By Post Id REST API is used to get comments from database by post id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<List<CommentDto>> getCommentsByPostID(@PathVariable("id") long id,
                                                                @RequestParam(value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false) long pageNo,
                                                                @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
                                                                @RequestParam(value = "sortBy" ,defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                                @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
    {
        return ResponseEntity.ok(commentService.getCommentsByPostId(id,pageNo,pageSize,sortBy,sortDir));
    }

    @GetMapping("/post/{postId}/comment/{commentId}")
    @Operation(
            summary = "Get Comment By Id REST API" ,
            description = "Get Comment By Id REST API is used to get comment from database by id."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId)
    {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }


    @PutMapping("/update/post/{postId}/comment/{commentId}")
    @Operation(
            summary = "Update Comment By Id REST API" ,
            description = "Update Comment By Id REST API is used to update comment in database by id."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,@PathVariable("postId") long postId,@PathVariable("commentId") long commentId)
    {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/post/{postId}/comment/{commentId}")
    @Operation(
            summary = "Delete Comment By Id REST API" ,
            description = "Delete Comment By Id REST API is used to delete comment in database by id."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<String> deletComment(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId)
    {
        return ResponseEntity.ok(commentService.deletComment(postId, commentId));
    }
}
