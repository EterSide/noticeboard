package com.example.noticeboard.repository;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUrl(String url);

    List<Image> findByPostIsNullAndCreatedAtBefore(LocalDateTime createdAt);

    boolean deleteByUrl(String url);

    List<Image> findByPost(Post post);

}
