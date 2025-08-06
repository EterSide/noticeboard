package com.example.noticeboard.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReplyComment {

    private long parentId;
    private long postId;
    private String content;

}
