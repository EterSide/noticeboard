package com.example.noticeboard.controller;

import ch.qos.logback.core.model.Model;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.service.FileService;
import com.example.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private FileService fileService;

    @GetMapping("posts")
    public String posts(Model model) {



        return "posts";

    }

    @GetMapping("add")
    public String add() {
        return "add_post";
    }

    @PostMapping("add")
    public String add(String title, String content) {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        Post save = postService.save(post);


        return "post/" + save.getId();
    }

    @GetMapping("{id}")
    public String post(Model model) {
        return "post";
    }




}
