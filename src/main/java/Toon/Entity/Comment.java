package Toon.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime; // LocalDateTime import 추가


@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "board_type")
    private String boardType;
    
    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) // 필요에 따라 사용할 타입 선택
    private LocalDateTime date;

    @Builder
    public Comment(Long commentId, String boardType , Long detailId, String contents, String nickname, LocalDateTime date) {
        this.commentId = commentId;
        this.boardType = boardType;
        this.detailId = detailId;
        this.contents = contents;
        this.nickname = nickname;
        this.date = date;
    }
}

