package Toon.Entity;

import java.time.LocalDateTime;

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

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString(exclude = "password")
@NoArgsConstructor

public class user {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UserId;

	@Column(name = "id", unique = true)
	private String id;

	@Column(name = "password")
	private String password;

	@Column(name = "email", unique = true)     
	private String email;

	@Column(name = "nickname", unique = true)
	private String nickname;

	@Column(name = "birth")
	private String birth;

	@Column(name = "passQuestion")
	private int passQuestion;

	@Column(name = "passAnswer")
	private String passAnswer;

	@Column(name = "userLevel")
	private int userLevel = 1;
	
	@Column(name = "userPoint")
	private int userPoint = 1000;
	
	@Column(name = "userAuthority")
	private String userAuthority = "일반회원";

	@Column(name = "punishLog", columnDefinition = "TEXT")
	private String punishLog;
	
	@Column(name = "recentLogin")
	@Temporal(TemporalType.TIMESTAMP) // 필요에 따라 사용할 타입 선택
	private LocalDateTime recentLogin;
	

	
	@Builder
	public user(Long userId, String id, String password, String email, String nickname, String birth, int passQuestion,
			String passAnswer, String punishLog, LocalDateTime recentLogin) {
		this.UserId = userId;
		this.id = id;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.birth = birth;
		this.passQuestion = passQuestion;
		this.passAnswer = passAnswer;
		this.punishLog = punishLog;
		this.recentLogin = recentLogin;
	}

}