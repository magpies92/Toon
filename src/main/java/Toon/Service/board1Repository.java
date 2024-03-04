package Toon.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import Toon.Entity.board1;

public interface board1Repository extends JpaRepository<board1, Long> {

	List<board1> findTop8ByOrderByBoard1IdDesc();

	Page<board1> findByTitleContainingOrderByDateDesc(String title, Pageable pageable);

	Page<board1> findByContentsContainingOrderByDateDesc(String contents, Pageable pageable);

	Page<board1> findByNicknameContainingOrderByDateDesc(String nickname, Pageable pageable);

	Page<board1> findAllByOrderByDateDesc(PageRequest of);

}
