package Toon.Service;

import java.util.Optional;


import org.springframework.stereotype.Service;

import Toon.Entity.user;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void register(user newUser) {
		// 여기에 회원 가입 로직을 작성하면 됩니다.
		userRepository.save(newUser);
	}

	public boolean isIdAvailable(String id) {
		// 주어진 아이디로 이미 저장된 사용자가 있는지 확인
		user existingUser = userRepository.findById(id).orElse(null);

		// 로그 추가
		if (existingUser != null) {
			System.out.println("User with ID " + id + " already exists.");
		} else {
			System.out.println("User with ID " + id + " is available.");
		}

		// 이미 사용 중인 경우 false 반환, 그렇지 않으면 true 반환
		return existingUser == null;
	}

	public boolean isNicknameAvailable(String nickname) {
		// 주어진 아이디로 이미 저장된 사용자가 있는지 확인
		user existingUser = userRepository.findByNickname(nickname).orElse(null);

		// 로그 추가
		if (existingUser != null) {
			System.out.println("User with nickname " + nickname + " already exists.");
		} else {
			System.out.println("User with nickname " + nickname + " is available.");
		}

		// 이미 사용 중인 경우 false 반환, 그렇지 않으면 true 반환
		return existingUser == null;
	}

	public boolean isEmailAvailable(String email) {
		// 주어진 아이디로 이미 저장된 사용자가 있는지 확인
		user existingUser = userRepository.findByEmail(email).orElse(null);

		// 로그 추가
		if (existingUser != null) {
			System.out.println("User with email " + email + " already exists.");
		} else {
			System.out.println("User with email " + email + " is available.");
		}

		// 이미 사용 중인 경우 false 반환, 그렇지 않으면 true 반환
		return existingUser == null;
	}
	
	public boolean login(String id, String password) {
		// 제공된 자격 증명이 유효한지 확인하는 로직을 여기에 작성하십시오.
		user existingUser = userRepository.findById(id).orElse(null);

		// 제공된 ID와 일치하는 사용자가 존재하고 비밀번호가 일치하는지 확인합니다.
		return existingUser != null && existingUser.getPassword().equals(password);
	}

	public user getUserInfo(String id) {
		// 사용자 정보 가져오는 로직을 구현
		// 여기서는 UserRepository 등을 사용하여 사용자 정보를 가져오는 예시를 들 수 있습니다.
		Optional<user> optionalUser = userRepository.findById(id);

		return optionalUser.orElse(null); // 존재하지 않으면 null 반환
	}

	public String findIdByEmail(String email) {
		Optional<String> optionalId = userRepository.findIdByEmail(email);

		// Optional에서 값을 추출하고, 값이 없으면 기본값으로 "not found"를 반환
		return optionalId.orElse("not found");
	}

	public boolean checkPasswordReset(String id, String email, int question, String answer) {
		// 데이터베이스에서 해당 유저 정보를 조회
		Optional<user> userOptional = userRepository.findById(id);

		// 유저 정보가 존재하고, 입력된 값들이 일치하는지 확인
		if (userOptional.isPresent()) {
			user user = userOptional.get();

			System.out.println("DB Email: " + user.getEmail());
			System.out.println("DB Question: " + user.getPassQuestion());
			System.out.println("DB Answer: " + user.getPassAnswer());

			return user.getEmail().equals(email) && user.getPassQuestion() == question
					&& user.getPassAnswer().equals(answer);
		}

		return false; // 해당 ID에 해당하는 유저가 존재하지 않음
	}

	// 비밀번호 업데이트 메서드
	@Transactional
	public boolean updatePass(String userId, String newPassword) {
		try {
			// 데이터베이스에서 사용자 정보를 가져옴
			user existingUser = userRepository.findById(userId).orElse(null);

			// 사용자가 존재하면 비밀번호 업데이트
			if (existingUser != null) {
				existingUser.setPassword(newPassword);
				userRepository.save(existingUser);
				return true; // 업데이트 성공
			} else {
				return false; // 사용자가 존재하지 않음
			}
		} catch (Exception e) {
			// 업데이트 실패시 예외 처리
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateUser(String id, String email, String nickname, String birth, int passQuestion,
			String passAnswer) {
		try {
			// 데이터베이스에서 사용자 정보를 가져옴
			user existingUser = userRepository.findById(id).orElse(null);

			// 사용자가 존재하면 비밀번호 업데이트
			if (existingUser != null) {
				existingUser.setEmail(email);
				existingUser.setNickname(nickname);
				existingUser.setBirth(birth);
				existingUser.setPassQuestion(passQuestion);
				existingUser.setPassAnswer(passAnswer);

				userRepository.save(existingUser);
				return true; // 업데이트 성공
			} else {
				return false; // 사용자가 존재하지 않음
			}
		} catch (Exception e) {
			// 업데이트 실패시 예외 처리
			e.printStackTrace();
			return false;
		}
	}

	// 유저 포인트 업데이트 및 레벨 업데이트
	public void updateUserPoint(String userId, int newPoint) {
		user user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			// 유저 포인트 업데이트
			if (newPoint > 9999) {
				newPoint = 9999; // 포인트가 9999를 초과하면 최대값인 9999로 설정
			}
			user.setUserPoint(newPoint);

			// 유저 레벨 업데이트
			if (newPoint >= 2000) {
				user.setUserLevel(2);
			}

			userRepository.save(user);
		}
	}

}
