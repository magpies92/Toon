package Toon.Service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Toon.Entity.Comment;

@Repository
public interface commentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByBoardTypeAndDetailId(String boardType, Long detailId);

	long countByBoardTypeAndDetailId(String string, Long board1Id);
	
	void deleteById(Long commentId);
}
