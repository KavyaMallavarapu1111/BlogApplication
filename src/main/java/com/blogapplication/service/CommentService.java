package com.blogapplication.service;

import com.blogapplication.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long id,long pageNo,int pageSize,String sortBy,String sortDir);
    CommentDto getCommentById(long postId,long commentId);
    CommentDto updateComment(long postId,long commentId,CommentDto commentDto);
    String deletComment(long postId,long commentId);
}
