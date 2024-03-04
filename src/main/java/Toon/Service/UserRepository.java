package Toon.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Toon.Entity.user;

public interface UserRepository extends JpaRepository<user, Long> {
	// 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
	Optional<user> findById(String id);

	Optional<user> findByNickname(String nickname);

	Optional<user> findByEmail(String email);
	
	// 이메일을 기반으로 사용자의 ID를 찾는 메서드
	@Query("SELECT u.id FROM user u WHERE u.email = :email")
	Optional<String> findIdByEmail(@Param("email") String email);

	void deleteById(String id);

	boolean existsById(String id); // 사용자가 존재하는지 확인하는 메서드

	Page<user> findAll(Pageable pageable);

	List<user> findAll();

	Page<user> findByUserAuthority(String string, Pageable pageable);
}
