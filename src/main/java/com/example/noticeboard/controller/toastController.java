package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.service.FileService;
import com.example.noticeboard.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class toastController {

    private final FileService fileService;
    private final ImageService imageService;

    @PostMapping("/api/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {

        Map<String, Object> response = new HashMap<>();

        try {
            // FileService를 통해 이미지 저장
            String imageUrl = fileService.saveImageFile(file);

            // 성공 응답
            response.put("imageUrl", imageUrl);
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());

            Image image = new Image();
            image.setFilename(file.getOriginalFilename());
            image.setUrl(imageUrl);
            image.setPost(null);
            image.setCreatedAt(LocalDateTime.now());

            imageService.save(image);

            System.out.println("이미지 업로드 성공: " + imageUrl);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 실패 응답
            response.put("error", e.getMessage());

            System.err.println("이미지 업로드 실패: " + e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    private boolean isValidImageExtension(String filename) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
        String lowerFilename = filename.toLowerCase();

        return Arrays.stream(allowedExtensions)
                .anyMatch(lowerFilename::endsWith);
    }

}
