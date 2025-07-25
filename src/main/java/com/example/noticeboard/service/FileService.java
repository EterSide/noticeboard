package com.example.noticeboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    // application.yml의 설정값을 가져옴 (기본값 설정)
    @Value("${spring.servlet.multipart.location:src/main/resources/static/uploads/}")
    private String uploadBasePath;

    // 허용되는 이미지 확장자
    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".webp");

    // 허용되는 MIME 타입
    private final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    /**
     * 이미지 파일을 서버에 저장
     * @param file 업로드된 파일
     * @return 저장된 파일의 상대 경로
     * @throws IOException 파일 저장 실패 시
     */
    public String saveImageFile(MultipartFile file) throws IOException {
        // 1. 파일 유효성 검사
        validateImageFile(file);

        // 2. 저장할 디렉토리 생성 (년/월 단위로 구분)
        String dateFolder = createDateFolder();
        String fullUploadPath = uploadBasePath + "images/" + dateFolder;

        File directory = new File(fullUploadPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("디렉토리 생성에 실패했습니다: " + fullUploadPath);
            }
        }

        // 3. 유니크한 파일명 생성
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

        // 4. 파일 저장
        Path filePath = Paths.get(fullUploadPath, uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 5. 웹에서 접근 가능한 상대 경로 반환
        return "/uploads/images/" + dateFolder + "/" + uniqueFileName;
    }

    /**
     * 이미지 파일 유효성 검사
     */
    private void validateImageFile(MultipartFile file) throws IOException {
        // 빈 파일 체크
        if (file.isEmpty()) {
            throw new IOException("업로드된 파일이 비어있습니다.");
        }

        // 파일 크기 체크 (50MB - yml 설정값과 동일)
        long maxSize = 50 * 1024 * 1024; // 50MB
        if (file.getSize() > maxSize) {
            throw new IOException("파일 크기가 50MB를 초과했습니다.");
        }

        // MIME 타입 체크
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            throw new IOException("지원하지 않는 파일 형식입니다. (JPG, PNG, GIF, WEBP만 허용)");
        }

        // 파일 확장자 체크
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !hasValidImageExtension(originalFilename)) {
            throw new IOException("유효하지 않은 파일 확장자입니다.");
        }
    }

    /**
     * 유효한 이미지 확장자인지 확인
     */
    private boolean hasValidImageExtension(String filename) {
        String lowerFilename = filename.toLowerCase();
        return ALLOWED_EXTENSIONS.stream()
                .anyMatch(lowerFilename::endsWith);
    }

    /**
     * 날짜별 폴더 생성 (예: 2024/07)
     */
    private String createDateFolder() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy/MM"));
    }

    /**
     * 유니크한 파일명 생성 (UUID + 원본 확장자)
     */
    private String generateUniqueFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        return UUID.randomUUID().toString() + extension;
    }

    /**
     * 파일 삭제 (게시글 삭제 시 사용)
     */
    public boolean deleteImageFile(String imagePath) {
        try {
            // "/uploads/images/2024/07/uuid.jpg" -> "src/main/resources/static/uploads/images/2024/07/uuid.jpg"
            String fullPath = uploadBasePath + imagePath.substring("/uploads/".length());
            Path path = Paths.get(fullPath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("파일 삭제 실패: " + imagePath + ", 오류: " + e.getMessage());
            return false;
        }
    }
}
