package Toon.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import Toon.Entity.video;

public interface videoRepository extends JpaRepository<video, Long> {
	
	Page<video> findByTitleContainingOrderByDateDesc(String title, Pageable pageable);

	Page<video> findByContentsContainingOrderByDateDesc(String contents, Pageable pageable);

	Page<video> findByNicknameContainingOrderByDateDesc(String nickname, Pageable pageable);
	
	Page<video> findAllByOrderByDateDesc(PageRequest of);

}
