package com.example.noticeboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 기본 게시판 기능
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer viewCount = 0;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0") 
    private Integer likeCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostCategory category = PostCategory.CHAT;

    // 책 관련 정보 (선택적 - 리뷰/토론 게시글용)
    private String bookTitle;
    
    private String author;
    
    // 책 평점
    @Column(columnDefinition = "INT CHECK (rating >= 1 AND rating <= 5)")
    private Integer rating;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean spoilerAlert = false;
    
    // 책 장르 열거형
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    // 공지글 관리
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isNotice = false;
    // 고정글 관리
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPinned = false;

    // 게시글 카테고리 열거형
    public enum PostCategory {
        REVIEW("도서 리뷰"),
        DISCUSSION("독서 토론"), 
        RECOMMENDATION("책 추천"),
        QUESTION("독서 질문"),
        CHAT("자유 잡담"),
        NEWS("도서 소식"),
        CHALLENGE("독서 챌린지"),
        QUOTE("좋은 구절");

        private final String displayName;

        PostCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // 책 장르 열거형
    public enum BookGenre {
        FICTION("소설"),
        NON_FICTION("비소설"),
        MYSTERY("추리/미스터리"),
        THRILLER("스릴러"),
        SF("SF/판타지"),
        ROMANCE("로맨스"),
        HISTORICAL("역사소설"),
        BIOGRAPHY("전기/자서전"),
        SELF_HELP("자기계발"),
        BUSINESS("경영/경제"),
        SCIENCE("과학"),
        PHILOSOPHY("철학"),
        PSYCHOLOGY("심리학"),
        HISTORY("역사"),
        TRAVEL("여행"),
        COOKING("요리"),
        ART("예술"),
        POETRY("시/에세이"),
        CHILDREN("아동/청소년"),
        COMIC("만화/그래픽노블"),
        RELIGION("종교/영성"),
        HEALTH("건강/의학"),
        EDUCATION("교육/학습"),
        OTHER("기타");

        private final String displayName;

        BookGenre(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
