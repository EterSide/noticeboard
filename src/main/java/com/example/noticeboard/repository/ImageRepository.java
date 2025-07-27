package com.example.noticeboard.repository;

import com.example.noticeboard.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    public Image findByUrl(String url);

    public List<Image> findByPostIsNullAndCreatedAtBefore(LocalDateTime createdAt);

    public boolean deleteByUrl(String url);

}
