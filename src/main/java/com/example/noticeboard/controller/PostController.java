package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "posts";
    }

    @GetMapping("/add")
    public String addPost() {
        return "add_post";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam("category") String category,
                         @RequestParam(value = "bookTitle", required = false) String bookTitle,
                         @RequestParam(value = "author", required = false) String author,
                         @RequestParam(value = "rating", required = false) Integer rating,
                         @RequestParam(value = "spoilerAlert", required = false) Boolean spoilerAlert,
                         @RequestParam(value = "genre", required = false) String genre,
                         @RequestParam(value = "userId", required = false) Long userId) {
        
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        
        // 카테고리 설정
        try {
            post.setCategory(Post.PostCategory.valueOf(category));
        } catch (IllegalArgumentException e) {
            post.setCategory(Post.PostCategory.CHAT); // 기본값
        }
        
        // 책 관련 정보 설정 (선택적)
        if (bookTitle != null && !bookTitle.trim().isEmpty()) {
            post.setBookTitle(bookTitle.trim());
        }
        if (author != null && !author.trim().isEmpty()) {
            post.setAuthor(author.trim());
        }
        if (rating != null && rating >= 1 && rating <= 5) {
            post.setRating(rating);
        }
        post.setSpoilerAlert(spoilerAlert != null ? spoilerAlert : false);
        
        // 장르 설정
        if (genre != null && !genre.trim().isEmpty()) {
            try {
                post.setGenre(Post.BookGenre.valueOf(genre));
            } catch (IllegalArgumentException e) {
                // 잘못된 장르면 null로 유지
            }
        }
        
        // 임시로 사용자 설정 (실제로는 세션에서 가져와야 함)
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            post.setUser(user);
        }
        
        // 게시글 저장
        postService.save(post);
        
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
