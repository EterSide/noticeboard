package com.example.noticeboard.service;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.entity.dto.CommentDto;
import com.example.noticeboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment addComment(Comment comment) {

        Comment savedComment = new Comment();
        savedComment.setId(comment.getId());
        savedComment.setContent(comment.getContent());
        savedComment.setPost(comment.getPost());
        savedComment.setUser(comment.getUser());
        savedComment.setParent(comment.getParent());
        savedComment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(savedComment);
    }

    public Optional<Comment> findByParent(long parentId) {
        return commentRepository.findById(parentId);
    }

    public Optional<List<Comment>> findByPost(Post post) {

        return commentRepository.findByPostIdWithReplies(post.getId());
    }

    public int countByPost(Post post) {
        return commentRepository.countByPostId(post.getId());
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

}
