package com.example.noticeboard.service;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.dto.CommentDto;
import com.example.noticeboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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

    public Comment findByParentId(Long parentId) {


        if(commentRepository.findByParentId(parentId).isPresent()) {
            return commentRepository.findByParentId(parentId).get();
        }
        return null;
    }

    public Comment findByCommentId(long commentId) {

        if(commentRepository.findById(commentId).isPresent()) {
            return commentRepository.findById(commentId).get();
        }

        return null;
    }

    public List<CommentDto> findByPost(Post post) {
        Optional<List<Comment>> comments = commentRepository.findByPostIdWithUser(post.getId());

        return comments.map(this::buildCommentDtoList).orElse(Collections.emptyList());

    }

    public int countByPost(Post post) {
        return commentRepository.countByPostId(post.getId());
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }



    public List<CommentDto> buildCommentDtoList(List<Comment> comments) {
        Map<Long, CommentDto> commentDtoMap = new HashMap<>();
        List<CommentDto> roots = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto(comment);
            commentDtoMap.put(comment.getId(), commentDto);
        }

        for(Comment comment : comments) {
            CommentDto commentDto = commentDtoMap.get(comment.getId());

            if(comment.getParent() != null) {
                CommentDto parentCommentDto = commentDtoMap.get(comment.getParent().getId());
                parentCommentDto.getReplies().add(commentDto);
            } else {
                roots.add(commentDto);
            }
        }
        return roots;
    }


}
