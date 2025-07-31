package com.example.noticeboard.controller;

import com.example.noticeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("comment")
    public String addComment(){

// 비동기..... 댓글 추천은 없앨까..
        return "redirect:/post/";
    }

}
