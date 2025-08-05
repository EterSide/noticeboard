package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.service.CommentService;
import com.example.noticeboard.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("add")
    public String addComment(@RequestParam long postId, @RequestParam long parentId, @RequestParam String content, HttpSession session) {

        Comment comment = new Comment();

        comment.setContent(content);
        Optional<Comment> byParent = commentService.findByParent(parentId);
        Optional<Post> postById = postService.getPostById(postId);

        byParent.ifPresent(comment::setParent);
        postById.ifPresent(comment::setPost);

        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            comment.setUser(user);
        }

        commentService.addComment(comment);

        return "redirect:/post/" + postId;
    }

    @PostMapping("reply")
    @ResponseBody
    public ResponseEntity<?> replyComment(@RequestParam long parentId, @RequestParam String content, HttpSession session) {

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setParent(commentService.findByParent(parentId).get());
        comment.setCreatedAt(LocalDateTime.now());
        commentService.addComment(comment);

        return ResponseEntity.ok().build();
    }

}
