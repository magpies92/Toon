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
@Table(name = "board1")
@Getter
@Setter
@ToString()
@NoArgsConstructor
public class board1 {

	@Id
	@Column(name = "board1Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long board1Id;

	@Column(name = "title")
	private String title;

	@Column(name = "id")
	private String id;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "contents", length = 10000)
	private String contents;

	@Column(name = "viewCount", nullable = false)
	private Integer viewCount = 0;

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP) // 필요에 따라 사용할 타입 선택
	private LocalDateTime date;

	@Builder
	public board1(Long board1Id, String title, String id, String nickname, String contents, Integer viewCount,
			LocalDateTime date) {
		this.board1Id = board1Id;
		this.title = title;
		this.id = id;
		this.nickname = nickname;
		this.contents = contents;
		this.viewCount = viewCount;
		this.date = date;
	}
}
