package com.example.noticeboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    // 자기 참조 관계 - 대댓글을 위한 부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Comment parent;

    // 자식 댓글들 (대댓글들)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    // 댓글 깊이 (0: 최상위 댓글, 1: 1단계 대댓글, 2: 2단계 대댓글...)
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer depth = 0;

    // 생성일시, 수정일시
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 댓글 상태 관리
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    // 삭제된 댓글의 표시 텍스트
    @Column
    private String deletedReason;

    // 좋아요 수
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likeCount = 0;

    // 편의 메서드들
    public void addReply(Comment reply) {
        this.replies.add(reply);
        reply.setParent(this);
        reply.setDepth(this.depth + 1);
    }

    public void removeReply(Comment reply) {
        this.replies.remove(reply);
        reply.setParent(null);
    }

    // 최상위 댓글인지 확인
    public boolean isTopLevel() {
        return this.parent == null;
    }

    // 대댓글인지 확인
    public boolean isReply() {
        return this.parent != null;
    }

    // 댓글이 삭제되었는지 확인
    public boolean isDeleted() {
        return this.isDeleted != null && this.isDeleted;
    }

    // 삭제 처리 (실제 삭제가 아닌 상태 변경)
    public void markAsDeleted(String reason) {
        this.isDeleted = true;
        this.deletedReason = reason != null ? reason : "사용자에 의해 삭제됨";
        this.content = "[삭제된 댓글입니다]";
    }

    // 표시할 내용 반환 (삭제된 댓글 처리)
    public String getDisplayContent() {
        return isDeleted() ? "[삭제된 댓글입니다]" : this.content;
    }
}
