package com.example.noticeboard.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {

    private long id;
    private String title;
    private String nickname;
    private LocalDateTime createdAt;
    private int comments;
    private int viewCount;


}
