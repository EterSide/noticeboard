package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.dto.PostDto;
import com.example.noticeboard.service.FileService;
import com.example.noticeboard.service.ImageService;
import com.example.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final ImageService imageService;
    private final FileService fileService;

    @GetMapping("posts")
    public String posts(Model model) {

        List<PostDto> postDtos = postService.getAllPosts();

        model.addAttribute("posts", postDtos);

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
    public String post(Model model, @PathVariable Long id, Session session ) {

        Optional<Post> postById = postService.getPostById(id);

        if(postById.isPresent()) {
            postById.get().setViewCount(postById.get().getViewCount() + 1);
            postService.save(postById.get());
            model.addAttribute("title", postById.get().getTitle());
            model.addAttribute("content", postById.get().getContent());

            return "post";
        }

        return "";
    }




}
