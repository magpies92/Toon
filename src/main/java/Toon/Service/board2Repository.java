package Toon.Service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import Toon.Entity.board2;

public interface board2Repository extends JpaRepository<board2, Long> {

	List<board2> findTop8ByOrderByBoard2IdDesc();
	
	Page<board2> findByTitleContainingOrderByDateDesc(String title, Pageable pageable);

	Page<board2> findByContentsContainingOrderByDateDesc(String contents, Pageable pageable);

	Page<board2> findByNicknameContainingOrderByDateDesc(String nickname, Pageable pageable);

	Page<board2> findAllByOrderByDateDesc(PageRequest of);
}
