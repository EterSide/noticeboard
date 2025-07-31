package com.example.noticeboard.repository;

import com.example.noticeboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public Post findById(long id);

}
