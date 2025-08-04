package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Image;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.entity.dto.CommentDto;
import com.example.noticeboard.service.CommentService;
import com.example.noticeboard.service.FileService;
import com.example.noticeboard.service.ImageService;
import com.example.noticeboard.service.PostService;
import com.mysql.cj.Session;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final ImageService imageService;
    private final FileService fileService;
    private final CommentService commentService;

    @GetMapping("posts")
    public String posts(Model model) {

        List<Post> allPosts = postService.getAllPosts();

        model.addAttribute("posts", allPosts);

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

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
    public String post(Model model, @PathVariable Long id, HttpSession session) {

        Optional<Post> postById = postService.getPostById(id);
        User member = (User) session.getAttribute("member");



        if(member != null) {
            model.addAttribute("user", member);
        }



        if(postById.isPresent()) {
            model.addAttribute("postId", postById.get().getId());
            model.addAttribute("title", postById.get().getTitle());
            model.addAttribute("content", postById.get().getContent());
            model.addAttribute("user", postById.get().getUser());
            model.addAttribute("member", session.getAttribute("member"));

            Optional<List<Comment>> comments = commentService.findByPost(postById.get());
            int count = commentService.countByPost(postById.get());

            model.addAttribute("count", count);

            model.addAttribute("comments", comments.get());

            return "post";
        }



        return "";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {



        return "update_post";
    }




}
