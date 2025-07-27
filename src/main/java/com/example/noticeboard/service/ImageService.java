package com.example.noticeboard.service;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void save(Image image) {

        Image img = new Image();
        img.setPost(image.getPost());
        img.setFilename(image.getFilename());
        img.setUrl(image.getUrl());

        imageRepository.save(image);
    }

    public Image findByUrl(String url) {
        return imageRepository.findByUrl(url);
    }

    public boolean deleteImageFile(String url) {
        return imageRepository.deleteByUrl(url);
    }


}
