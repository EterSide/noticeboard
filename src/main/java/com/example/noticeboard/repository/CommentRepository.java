package com.example.noticeboard.repository;

import com.example.noticeboard.entity.Comment;
import com.example.noticeboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(long id);
    Optional<List<Comment>> findByPost(Post post);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.user WHERE c.post.id = :postId")
    Optional<List<Comment>> findByPostIdWithUser(@Param("postId") Long postId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    int countByPostId(@Param("postId") Long postId);

    Optional<Comment> findByParentId(long parentId);

    @Query("select count(c) from Comment c where c.post.id = :postId")
    int countCommentByPost(Post post);

    int countByPost(Post post);

}
