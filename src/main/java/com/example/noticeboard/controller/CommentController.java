package com.example.noticeboard.controller;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.entity.dto.ReplyComment;
import com.example.noticeboard.service.CommentService;
import com.example.noticeboard.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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
        Comment parentComment = commentService.findByCommentId(parentId);
        Post post = postService.getPostById(postId);

        comment.setParent(parentComment);
        comment.setPost(post);

        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            comment.setUser(user);
        }

        commentService.addComment(comment);

        return "redirect:/post/" + postId;
    }

    @PostMapping("reply")
    @ResponseBody
    public ResponseEntity<?> replyComment(@RequestBody ReplyComment replyComment, HttpSession session) {

        System.out.println("대댓글 요청 발생");

        User user = (User) session.getAttribute("user");

        Comment comment = new Comment();
        comment.setContent(replyComment.getContent());
        Comment parentComment = commentService.findByCommentId(replyComment.getParentId());
        comment.setParent(parentComment);
        comment.setPost(postService.getPostById(replyComment.getPostId()));

        comment.setCreatedAt(LocalDateTime.now());
        if(user != null) {
            comment.setUser(user);
        }
        commentService.addComment(comment);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentid}/edit")
    public ResponseEntity<?> editComment(@RequestBody ReplyComment replyComment, @PathVariable Long commentid, HttpSession session) throws AccessDeniedException {
        
        System.out.println("댓글 수정 요청 발생");

        User user = (User) session.getAttribute("user");

        commentService.updateContent(commentid, replyComment.getContent(), user.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, HttpSession session) throws AccessDeniedException {

        User user = (User) session.getAttribute("user");

        Comment comment = commentService.findByCommentId(commentId);

        comment.setIsDeleted(true);
        comment.setDeletedReason("");

        commentService.deleteComment(commentId, user.getId(), false);

        return ResponseEntity.ok().build();

    }

}
