package com.blogapplication.service.Impl;

import com.blogapplication.Entity.Comment;
import com.blogapplication.Entity.Post;
import com.blogapplication.exception.BlogAPIException;
import com.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.payload.CommentDto;
import com.blogapplication.repository.CommentRepository;
import com.blogapplication.repository.PostRepository;
import com.blogapplication.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.mapper = mapper;
    }

    private CommentDto toDto(Comment comment)
    {
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
        return commentDto;
    }

    private Comment toEntity(CommentDto commentDto)
    {
        Comment comment = mapper.map(commentDto,Comment.class);
//        comment.setBody(commentDto.getBody());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setId(commentDto.getId());
        return comment;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = toEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        CommentDto newCommentDto = toDto(newComment);
        return newCommentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long id,long pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of((int) pageNo,pageSize,sort);
        List<Comment> comments = commentRepository.findByPostId(id,pageable);
        List<CommentDto> newComments = comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
        return newComments;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"this comment does not exist for this post");
        }
        return toDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if (!comment.getPost().getId().equals(post.getId())) throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not exist with this post");
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        return toDto(updatedComment);
    }

    @Override
    public String deletComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not find with this post");
        }
        commentRepository.delete(comment);
        return "Comment deleted successfully....";
    }
}
