package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.service.FileService;
import com.example.noticeboard.service.ImageService;
import com.example.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final ImageService imageService;
    private final FileService fileService;

    @GetMapping("posts")
    public String posts(Model model) {

        return "posts";

    }

    @GetMapping("add")
    public String add() {
        return "add_post";
    }

    @PostMapping("add")
    public String add(@RequestParam String title, @RequestParam String content) {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());

        Post save = postService.save(post);

        List<String> strings = fileService.extractImageSrcUrls(content);
        
        for(String str : strings) {
            if(imageService.findByUrl(str) != null) {
                System.out.println("추출된 URL: [" + str + "]");
                Image byUrl = imageService.findByUrl(str);
                byUrl.setPost(save);
                imageService.save(byUrl);
            }
        }

        return "redirect:/post/" + save.getId();
    }

    @GetMapping("{id}")
    public String post(Model model) {




        return "post";
    }




}
