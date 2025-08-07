package com.example.noticeboard.repository;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public Optional<Post> findById(long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :id")
    User findByIdWithPosts(@Param("id") Long id);

}
