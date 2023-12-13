package com.blogapplication.service;

import com.blogapplication.payload.PostDto;
import com.blogapplication.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto getPost(Long id);
    PostResponse getPosts(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto updatePost(PostDto postDto,Long id);
    void deletePost(Long id);
    List<PostDto> findByCategoryId(Long id);
}
