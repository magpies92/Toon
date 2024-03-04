package Toon.Service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import Toon.Entity.news;

public interface newsRepository extends JpaRepository<news, Long> {

	List<news> findTop7ByOrderByNewsIdDesc();
	
	Page<news> findByTitleContainingOrderByDateDesc(String title, Pageable pageable);

	Page<news> findByContentsContainingOrderByDateDesc(String contents, Pageable pageable);

	Page<news> findByNicknameContainingOrderByDateDesc(String nickname, Pageable pageable);
	
	Page<news> findAllByOrderByDateDesc(PageRequest of);
}
