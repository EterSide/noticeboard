package com.example.noticeboard.controller;

import com.example.noticeboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    PostService postService;

    public String postList(Model model) {

        model.addAttribute("posts", postService.getAllPosts());

        return "postList";
    }

}
