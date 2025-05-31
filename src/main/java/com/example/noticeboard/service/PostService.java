package com.example.noticeboard.service;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

}
