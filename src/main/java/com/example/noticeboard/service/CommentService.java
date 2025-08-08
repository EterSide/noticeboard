package com.example.noticeboard.service;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.dto.CommentDto;
import com.example.noticeboard.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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

    @Transactional
    public void updateContent(Long commentId, String newContent, Long loginId) throws AccessDeniedException {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if(comment.getUser() == null || !comment.getUser().getId().equals(loginId)) {
            throw new AccessDeniedException("권한없음");
        }

        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());

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

    @Transactional
    public void deleteComment(Long commentId, Long loginUserId, boolean isAdmin) throws AccessDeniedException {
        Comment c = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!isAdmin) {
            if (c.getUser() == null || !c.getUser().getId().equals(loginUserId)) {
                throw new AccessDeniedException("삭제 권한 없음");
            }
        }

        if (Boolean.TRUE.equals(c.getIsDeleted())) {
            return; // 이미 삭제됨
        }

        c.setIsDeleted(true);
        c.setDeletedReason("사용자에 의해 삭제됨");
        // save() 불필요 — 영속상태 + 트랜잭션이면 dirty checking
    }


}
