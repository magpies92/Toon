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
@Table(name = "teamInfo")
@Getter
@Setter
@ToString()
@NoArgsConstructor
public class teamInfo {

	@Id
	@Column(name = "teamInfoId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamInfoId;

	@Column(name = "category")
	private String category;

	@Column(name = "contents" , length = 10000)
	private String contents;

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP) // 필요에 따라 사용할 타입 선택
	private LocalDateTime date;

	@Builder
	public teamInfo(Long teamInfoId,  String category, String contents, 
			LocalDateTime date) {
		this.teamInfoId = teamInfoId;
		this.category = category;
		this.contents = contents;
		this.date = date;
	}
}
