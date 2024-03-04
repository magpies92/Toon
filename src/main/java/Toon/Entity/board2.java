package Toon.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime; // LocalDateTime import 추가

@Entity
@Table(name = "board2")
@Getter
@Setter
@ToString()
@NoArgsConstructor
public class board2 {

    @Id
    @Column(name = "board2Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board2Id;

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
    private LocalDateTime date; // Date에서 LocalDateTime으로 변경

    @Builder
    public board2(Long board2Id, String title, String id, String nickname, String contents, int viewCount, LocalDateTime date) {
        this.board2Id = board2Id;
        this.title = title;
        this.id = id;
        this.nickname = nickname;
        this.contents = contents;
        this.viewCount = viewCount;
        this.date = date;
    }
}
