package com.example.noticeboard.service;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.User;
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

    public User findByIdWithPosts(Long id) {
        return postRepository.findByIdWithPosts(id);
    }

    @Transactional
    public Post save(Post post) {

        Post po = new Post();
        po.setId(post.getId());
        po.setComments(post.getComments());
        po.setUser(post.getUser());
        po.setTitle(post.getTitle());
        po.setContent(post.getContent());


//        po.setGenre(post.getGenre());
//        po.setRating(post.getRating());
//        po.setIsNotice(post.getIsNotice());
//        po.setIsPinned(post.getIsPinned());
//        po.setCategory(post.getCategory());
//        po.setBookTitle(post.getBookTitle());
//        po.setAuthor(post.getAuthor());
//        po.setSpoilerAlert(post.getSpoilerAlert());
        po.setCreatedAt(post.getCreatedAt());
        po.setUpdatedAt(post.getUpdatedAt());
        po.setViewCount(post.getViewCount());
        po.setLikeCount(post.getLikeCount());
//        po.setSpoilerAlert(post.getSpoilerAlert());

        return postRepository.save(po);
    }
}
