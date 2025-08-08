package com.example.noticeboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_like",
        uniqueConstraints = @UniqueConstraint(name="uq_user_post", columnNames={"user_id","post_id"}))
@Getter
@Setter
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private byte value; // 1 or -1

    @CreationTimestamp
    private LocalDateTime createdAt;

    public PostLike(Long userId, Post post, byte value) {
        this.userId = userId;
        this.post = post;
        this.value = value;
    }
}

