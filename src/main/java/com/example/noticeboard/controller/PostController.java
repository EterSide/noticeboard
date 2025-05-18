package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    PostService postService;

    public String postList(Model model) {

        model.addAttribute("posts", postService.getAllPosts());

        return "postList";
    }

    @GetMapping("/add")
    public String addPost() {
        return "add_post";
    }

    @PostMapping("/add/{post_id}")
    public String addPost(Model model, Post post) {

        postService.save(post);
        model.addAttribute("post", post);

        return "redirect:/post/" + post.getId();
    }

}
