package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.service.CommentService;
import com.example.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/comment")
    public String addComment(Session session, @RequestParam String content, @RequestParam long userId, @RequestParam long postId) {

        Optional<Post> postById = postService.getPostById(postId);


        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(postById.get());

        commentService.save(comment);

        return "redirect:/post" + postId;
    }

}
