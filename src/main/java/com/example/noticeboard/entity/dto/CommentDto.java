package com.example.noticeboard.entity.dto;

import com.example.noticeboard.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentDto {

    String content;
    String nickname;
    List<Comment> replies;
    LocalDateTime createdAt;

}
