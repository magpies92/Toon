package Toon.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import Toon.Entity.matchday;


public interface matchdayRepository extends JpaRepository<matchday, Long> {
	
	Page<matchday> findByTitleContainingOrderByDateDesc(String title, Pageable pageable);

	Page<matchday> findByContentsContainingOrderByDateDesc(String contents, Pageable pageable);

	Page<matchday> findByNicknameContainingOrderByDateDesc(String nickname, Pageable pageable);

	Page<matchday> findAllByOrderByDateDesc(PageRequest of);
}
