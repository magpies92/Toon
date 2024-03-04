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
@Table(name = "notice")
@Getter
@Setter
@ToString()
@NoArgsConstructor
public class notice {

    @Id
    @Column(name = "noticeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(name = "title")
    private String title;

    @Column(name = "id")
    private String id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "contents" , length = 10000)
    private String contents;
    
    @Column(name = "viewCount")
	private int viewCount;


    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) // 필요에 따라 사용할 타입 선택
    private LocalDateTime date;

    @Builder
    public notice(Long noticeId, String title, String id, String nickname, String contents, int viewCount, LocalDateTime date) {
        this.noticeId = noticeId;
        this.title = title;
        this.id = id;
        this.nickname = nickname;
        this.contents = contents;
        this.viewCount = viewCount;
        this.date = date;
    }
}

