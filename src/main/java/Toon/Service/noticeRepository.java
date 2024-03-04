package Toon.Service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import Toon.Entity.notice;

public interface noticeRepository extends JpaRepository<notice, Long> {
	
	List<notice> findTop5ByOrderByNoticeIdDesc();

	Page<notice> findByTitleContainingOrderByDateDesc(String title, Pageable pageable);

	Page<notice> findByContentsContainingOrderByDateDesc(String contents, Pageable pageable);

	Page<notice> findByNicknameContainingOrderByDateDesc(String nickname, Pageable pageable);
	
	Page<notice> findAllByOrderByDateDesc(PageRequest of);
}
