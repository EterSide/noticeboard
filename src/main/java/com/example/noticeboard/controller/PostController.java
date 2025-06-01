package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "postList";
    }

    @GetMapping("/add")
    public String addPost() {
        return "add_post";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam(value = "imageIds", required = false) String imageIds,
                         @RequestParam(value = "userId", required = false) Long userId) {
        
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        
        // 임시로 사용자 설정 (실제로는 세션에서 가져와야 함)
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            post.setUser(user);
        }
        
        // 이미지 ID들을 파싱
        List<Long> imageIdList = null;
        if (imageIds != null && !imageIds.trim().isEmpty()) {
            imageIdList = Arrays.stream(imageIds.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
        
        // 게시글과 이미지 저장
        if (imageIdList != null && !imageIdList.isEmpty()) {
            postService.saveWithImages(post, imageIdList);
        } else {
            postService.save(post);
        }
        
        return "redirect:/post/list";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id).orElse(null);
        if (post == null) {
            return "redirect:/post/list";
        }
        model.addAttribute("post", post);
        return "post";
    }
}
