package com.example.noticeboard.service;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post save(Post post) {

        Post po = new Post();
        po.setId(post.getId());
        po.setComments(post.getComments());
        po.setUser(post.getUser());
        po.setTitle(post.getTitle());
        po.setContent(post.getContent());

        // 책 장르 열거형
        po.setGenre(post.getGenre());
        // 책 평점
        po.setRating(post.getRating());
        // 공지글 관리
        po.setIsNotice(post.getIsNotice());
        // 고정글 관리
        po.setIsPinned(post.getIsPinned());
        po.setCategory(post.getCategory());
        po.setBookTitle(post.getBookTitle());
        po.setAuthor(post.getAuthor());
        po.setSpoilerAlert(post.getSpoilerAlert());
        po.setCreatedAt(post.getCreatedAt());
        po.setUpdatedAt(post.getUpdatedAt());
        po.setViewCount(post.getViewCount());
        po.setLikeCount(post.getLikeCount());
        po.setSpoilerAlert(post.getSpoilerAlert());

        return postRepository.save(po);
    }
}
