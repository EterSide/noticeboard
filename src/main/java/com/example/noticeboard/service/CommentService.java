package com.example.noticeboard.service;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
import com.example.noticeboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(Comment comment) {

        Comment comm = new Comment();
        comm.setId(comment.getId());
        comm.setPost(comment.getPost());
        comm.setUser(comment.getUser());
        comm.setContent(comment.getContent());
        comm.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comm);

    }

}
