package Toon.Controller;

import Toon.Entity.user;
import Toon.Service.UserRepository;
import Toon.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class userController {

	@Autowired
	private UserService userService;
	// UserService는 회원 등록과 관련된 비즈니스 로직을 담당하는 서비스 클래스입니다.

	@Autowired
	private UserRepository UserRepository;

	@PostMapping("/register")
	public String register(@ModelAttribute("user") user user) {

		System.out.println("ID: " + user.getId());
		System.out.println("Password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("nickname: " + user.getNickname());
		System.out.println("birth: " + user.getBirth());
		System.out.println("passQuestion: " + user.getPassQuestion());
		System.out.println("passAnswer: " + user.getPassAnswer());

		// 회원 등록 로직 수행 후 적절한 화면으로 이동
		userService.register(user); // UserService에서 실제로 회원 등록을 수행하는 메서드 호출
		return "redirect:/main";
	}

	// 새로운 메서드 추가: 아이디 중복 체크
	@GetMapping("/checkIdAvailability")
	@ResponseBody
	public String checkIdAvailability(@RequestParam(name = "id") String id) {
		// 아이디 중복 체크 로직 수행
		boolean isAvailable = userService.isIdAvailable(id);

		// 결과에 따라 문자열 반환
		if (isAvailable == true) {
			return "available"; // 사용 가능한 아이디
		} else {
			return "not available"; // 중복된 아이디
		}
	}

	// 새로운 메서드 추가: 닉네임 중복 체크
	@GetMapping("/checkNicknameAvailability")
	@ResponseBody
	public String checkNicknameAvailability(@RequestParam(name = "nickname") String nickname) {
		// 아이디 중복 체크 로직 수행
		boolean isAvailable = userService.isNicknameAvailable(nickname);

		// 결과에 따라 문자열 반환
		if (isAvailable == true) {
			return "available"; // 사용 가능한 닉네임
		} else {
			return "not available"; // 중복된 닉네임
		}
	}

	// 새로운 메서드 추가: 이메일 중복 체크
		@GetMapping("/checkEmailAvailability")
		@ResponseBody
		public String checkEmailAvailability(@RequestParam(name = "email") String email) {
			// 이메일 중복 체크 로직 수행
			boolean isAvailable = userService.isEmailAvailable(email);

			// 결과에 따라 문자열 반환
			if (isAvailable == true) {
				return "available"; // 사용 가능한 아이디
			} else {
				return "not available"; // 중복된 아이디
			}
		}
	
	
	@Autowired
	private HttpSession session;

	@PostMapping("/login")
	public String login(@RequestParam(name = "id") String id, @RequestParam(name = "pass") String pass) {
		// 로그인 로직 수행
		boolean loginSuccess = userService.login(id, pass);

		// 로그인 성공 시 세션에 사용자 정보 저장
		if (loginSuccess) {
			user userInfo = UserRepository.findById(id).orElse(null);

			if (userInfo != null) {
				// 현재 시간을 가져와서 recentLogin 필드에 저장
				userInfo.setRecentLogin(LocalDateTime.now());

				// 사용자 정보를 업데이트
				UserRepository.save(userInfo);

				session.setAttribute("userId", id);
				session.setAttribute("userNickname", userInfo.getNickname());
				session.setAttribute("userEmail", userInfo.getEmail());
				session.setAttribute("userBirth", userInfo.getBirth());
				session.setAttribute("userPassQuestion", userInfo.getPassQuestion());
				session.setAttribute("userPassAnswer", userInfo.getPassAnswer());
				session.setAttribute("userLevel", userInfo.getUserLevel());
				session.setAttribute("userPoint", userInfo.getUserPoint());
				session.setAttribute("userAuthority", userInfo.getUserAuthority());
				System.out.println("After saving to session: " + session.getAttribute("userId"));
				System.out.println("After saving to session: " + session.getAttribute("userPassQuestion"));

				return "redirect:/main"; // 여기서 리다이렉션을 문자열로 설정
			} else {
				return "msg"; // 사용자 정보를 찾을 수 없을 때의 처리
			}
		} else {
			return "msg";
		}
	}

	@PostMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request) {
	    // 세션을 가져옴
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        // 세션을 완전히 초기화하여 로그아웃
	        session.invalidate();
	        System.out.println("세션 완전히 초기화됨");
	        return "success";
	    }
	    // 세션이 이미 종료되었을 경우에 대한 처리를 생략함
	    return "failure"; // 실패한 경우 "failure" 반환
	}

	@GetMapping("/findIdByEmail")
	@ResponseBody
	public String findIdByEmail(@RequestParam(name = "email") String email) {
		// 이메일을 사용하여 id 찾는 로직 수행
		String foundId = userService.findIdByEmail(email);

		// 결과에 따라 문자열 반환
		if (foundId != null) {
			return foundId; // 해당 이메일에 대한 id가 존재함
		} else {
			return "not found"; // 해당 이메일에 대한 id가 존재하지 않음
		}
	}

	@PostMapping("/resetPassword")
	@ResponseBody
	public ResponseEntity<String> resetPassword(@RequestParam(name = "id") String id,
			@RequestParam(name = "email") String email, @RequestParam(name = "question") int question,
			@RequestParam(name = "answer") String answer) {
		// 로깅 대신 sysout 사용
		System.out.println("Received resetPassword request. id: " + id + ", email: " + email + ", question: " + question
				+ ", answer: " + answer);

		// 여기서 userService를 통해 데이터베이스에서 해당 정보를 조회하고 비밀번호를 확인
		boolean isValid = userService.checkPasswordReset(id, email, question, answer);

		if (isValid) {
			// 성공적으로 확인되었을 때, 클라이언트에게 "success" 및 비밀번호 입력 창 표시 명령을 전달
			return ResponseEntity.ok("success");
		} else {
			// 유효하지 않을 때, 클라이언트에게 "invalid" 전달
			System.out.println("Invalid resetPassword request. id: " + id + ", email: " + email + ", question: "
					+ question + ", answer: " + answer);
			return ResponseEntity.ok("invalid");
		}
	}

	@PostMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(@RequestParam("userId") String userId,
			@RequestParam("newPassword") String newPassword) {
		// 비밀번호 업데이트 서비스 메서드 호출
		boolean success = userService.updatePass(userId, newPassword);

		if (success) {
			return "success"; // 비밀번호 업데이트 성공
		} else {
			return "failure"; // 비밀번호 업데이트 실패
		}
	}

	@PostMapping("/updateUser")
	@ResponseBody
	public String updateUser(@RequestParam("id") String id, @RequestParam("email") String email,
			@RequestParam("nickname") String nickname, @RequestParam("birth") String birth,
			@RequestParam("passQuestion") int passQuestion, @RequestParam("passAnswer") String passAnswer) {
		// updateUser 메소드를 호출하여 사용자 정보 업데이트
		boolean success = userService.updateUser(id, email, nickname, birth, passQuestion, passAnswer);

		// 업데이트 결과에 따라 적절한 뷰 반환
		if (success) {

			session.setAttribute("userEmail", email);
			session.setAttribute("userNickname", nickname);
			session.setAttribute("userBirth", birth);
			session.setAttribute("userPassQuestion", passQuestion);
			session.setAttribute("userPassAnswer", passAnswer);

			return "success"; // 성공 시
		} else {
			return "error"; // 실패 시
		}
	}

	@GetMapping("/admin")
	public String admin(Model model, @PageableDefault(size = 10, sort = "userLevel", direction = Direction.DESC) Pageable pageable) {
		Page<user> userPage = UserRepository.findAll(pageable);
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("page", userPage);
		return "admin"; // admin.html과 같은 Thymeleaf 템플릿 파일을 반환합니다.
	}

	@GetMapping("/punish")
	public String punish(Model model, @PageableDefault(size = 10, sort = "userLevel", direction = Direction.DESC) Pageable pageable) {
	    // "징계중"인 사용자만 가져오는 쿼리
	    Page<user> userPage = UserRepository.findByUserAuthority("징계중", pageable);
	    List<user> userList = userPage.getContent();
	    model.addAttribute("users", userList);
	    model.addAttribute("page", userPage);
	    return "punish"; // punish.html과 같은 Thymeleaf 템플릿 파일을 반환합니다.
	}



	@Transactional
	@PostMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(@RequestParam("id") String id) {
		try {
			UserRepository.deleteById(id);
			return "success"; // 삭제 성공 시 success 반환
		} catch (Exception e) {
			e.printStackTrace();
			return "failure"; // 삭제 실패 시 failure 반환
		}
	}

	// 사용자 권한 업데이트 엔드포인트
	@PostMapping("/updateUserAuthority")
	public ResponseEntity<String> updateUserAuthority(@RequestParam("id") String id,
			@RequestParam("authority") String authority) {
		// 사용자의 권한을 업데이트하고 결과를 반환합니다.
		if (UserRepository.existsById(id)) {
			// 사용자가 존재하면 권한을 업데이트합니다.
			user existingUser = UserRepository.findById(id).orElse(null);
			if (existingUser != null) {
				existingUser.setUserAuthority(authority); // 여기에 새로운 권한을 설정해주세요
				UserRepository.save(existingUser);
				return new ResponseEntity<>("success", HttpStatus.OK);
			} else {
				// 사용자를 찾을 수 없는 경우
				return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
			}
		} else {
			// 사용자가 존재하지 않는 경우
			return new ResponseEntity<>("사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/updatePunishment")
	public ResponseEntity<String> updateUserInfo(@RequestParam("id") String id,
			@RequestParam("authority") String authority, @RequestParam("punishLog") String punishLog) {
		// 사용자의 권한을 업데이트하고 결과를 반환합니다.
		if (UserRepository.existsById(id)) {
			// 사용자가 존재하면 권한을 업데이트합니다.
			user existingUser = UserRepository.findById(id).orElse(null);
			if (existingUser != null) {
				existingUser.setUserAuthority(authority); // 여기에 새로운 권한을 설정해주세요
				existingUser.setPunishLog(punishLog); // 여기에 새로운 징계내역을 설정해주세요
				UserRepository.save(existingUser);
				return new ResponseEntity<>("success", HttpStatus.OK);
			} else {
				// 사용자를 찾을 수 없는 경우
				return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
			}
		} else {
			// 사용자가 존재하지 않는 경우
			return new ResponseEntity<>("사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}

	}

}
