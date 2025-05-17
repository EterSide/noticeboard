package com.example.noticeboard.service;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostService {

    PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

}
