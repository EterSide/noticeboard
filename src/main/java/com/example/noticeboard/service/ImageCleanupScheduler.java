package com.example.noticeboard.service;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCleanupScheduler {

    private final ImageRepository imageRepository;


    @Scheduled(cron = "0 0 3 * * *")
    public void cleanUpUnusedImages() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(24);
        List<Image> expiredImages = imageRepository.findByPostIsNullAndCreatedAtBefore(cutoff);

        for (Image image : expiredImages) {
            boolean delete = imageRepository.deleteByUrl(image.getUrl());
            if(delete) {
                imageRepository.delete(image);
                log.info("🧹 삭제 완료: {}", image.getUrl());
            } else {
                log.warn("⚠️ 삭제 실패: {}", image.getUrl());
            }
        }
    }

}
