package com.example.noticeboard.service;

import com.example.noticeboard.entity.Image;
import com.example.noticeboard.entity.Post;
import com.example.noticeboard.repository.ImageRepository;
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
    private final ImageRepository imageRepository;

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

        return postRepository.save(po);
    }

    @Transactional
    public Post saveWithImages(Post post, List<Long> imageIds) {
        // 게시글 먼저 저장
        Post savedPost = postRepository.save(post);
        
        // 이미지들을 게시글과 연결
        if (imageIds != null && !imageIds.isEmpty()) {
            List<Image> images = imageRepository.findAllById(imageIds);
            for (Image image : images) {
                image.setPost(savedPost);
            }
            imageRepository.saveAll(images);
            savedPost.getImages().addAll(images);
        }
        
        return savedPost;
    }
}
