package com.example.noticeboard.service;

import com.example.noticeboard.entity.Post;
import com.example.noticeboard.entity.dto.PostDto;
import com.example.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        for(Post post : posts) {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setTitle(post.getTitle());
            if(post.getUser() == null) {
                postDto.setNickname("ㅇㅇ");
            } else {
                postDto.setNickname(post.getUser().getNickname());
            }

            postDto.setCreatedAt(post.getCreatedAt());
            postDto.setViewCount(post.getViewCount());
            postDto.setComments(post.getComments().size());

            postDtos.add(postDto);
        }


        return postDtos;
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
