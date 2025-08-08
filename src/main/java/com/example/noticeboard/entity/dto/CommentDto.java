package com.example.noticeboard.entity.dto;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private String content;
    private String nickname;
    private Long userId;
    private LocalDateTime createdAt;
    private List<CommentDto> replies;
    private boolean isDeleted;
    private String deleteReason;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = (comment.getUser() != null) ? comment.getUser().getNickname() : "익명";
        this.userId = comment.getUser() != null ? comment.getUser().getId() : null; // 🔥 요렇게!
        this.createdAt = comment.getCreatedAt();
        this.replies = new ArrayList<>();
        this.isDeleted = comment.isDeleted();
        this.deleteReason = "삭제되었습니다.";
    }
}
