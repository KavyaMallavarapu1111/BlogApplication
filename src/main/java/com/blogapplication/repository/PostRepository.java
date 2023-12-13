package com.blogapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blogapplication.Entity.Post;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long id);
}
