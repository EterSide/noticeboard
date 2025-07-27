package com.example.noticeboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String url;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

//    @Column(nullable = false)
//    private int image_order;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

}
