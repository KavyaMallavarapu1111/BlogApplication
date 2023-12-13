package com.blogapplication.service.Impl;

import com.blogapplication.Entity.Category;
import com.blogapplication.Entity.Post;
import com.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.payload.PostDto;
import com.blogapplication.payload.PostResponse;
import com.blogapplication.repository.CategoryRepository;
import com.blogapplication.repository.PostRepository;
import com.blogapplication.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }


    private Post toPost(PostDto postDto)
    {
        Post post=mapper.map(postDto,Post.class);
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }
    private PostDto toPostDTO(Post post)
    {
        PostDto postDto = mapper.map(post,PostDto.class);
//        postDto.setId(post.getId());
//        postDto.setDescription(post.getDescription());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //conert DTO to entity
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("category","id",postDto.getCategoryId()));
        Post post = toPost(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return toPostDTO(newPost);
    }


    @Override
    public PostDto getPost(Long id) {
        Post post =postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
       // Post post = postRepository.getById(id);
        return toPostDTO(post);
    }

    @Override
    public PostResponse getPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        //This supports  pagination only
        //Pageable pageable= PageRequest.of(pageNo,pageSize);
        //Page<Post> posts = postRepository.findAll(pageable);
        //List<Post> listOfPosts = posts.getContent();

        //This supports pagination and sorting as well
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> toPostDTO(post)).collect(Collectors.toList());
        PostResponse postResponse =  new PostResponse();
        postResponse.setContent(content);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        return postResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto,Long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("category","id",postDto.getCategoryId()));
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setCategory(category);
        Post p=postRepository.save(post);
        return toPostDTO(p);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> findByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        List<Post> posts = postRepository.findByCategoryId(id);
        return posts.stream().map((post) -> mapper.map(post,PostDto.class)).toList();
    }
}
