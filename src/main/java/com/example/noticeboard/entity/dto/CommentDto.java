package com.example.noticeboard.entity.dto;

import com.example.noticeboard.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private String content;
    private String nickname;
    private List<CommentDto> replies;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = (comment.getUser() != null) ? comment.getUser().getNickname() : "익명";
        this.replies = new ArrayList<>();
    }
}
