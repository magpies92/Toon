package Toon.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

import Toon.Entity.Comment;
import Toon.Entity.board2;
import Toon.Service.board2Repository;
import Toon.Service.commentRepository;

@Controller
@RequestMapping("/board2")
public class board2Controller {

	@Autowired
	private board2Repository board2Repository;

	@Autowired
	private commentRepository commentRepository;

	@GetMapping
	public String board2List(Model model, Pageable pageable) {
		// 한 페이지에 보여줄 아이템 수를 10으로 설정하고 역순으로 정렬
		Page<board2> board2Page = board2Repository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "board2Id")));

		model.addAttribute("board2Page", board2Page);

		// 각 게시글 별 댓글 수 가져오기
		List<board2> allboard2 = board2Repository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (board2 b : allboard2) {
			long count = commentRepository.countByBoardTypeAndDetailId("board2", b.getBoard2Id());
			if (count != 0) {
				commentCounts.put(b.getBoard2Id(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "board2";
	}

	@GetMapping("/{board2Id}")
	public String board2Detail(@PathVariable(name = "board2Id") Long board2Id, Model model, Pageable pageable) {
		// 상세 페이지에서는 특정 게시물을 표시
		board2 board2 = board2Repository.findById(board2Id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid board2Id: " + board2Id));

		board2.setViewCount(board2.getViewCount() + 1);
		board2Repository.save(board2);

		model.addAttribute("boardType", "board2");
		model.addAttribute("board2Id", board2.getBoard2Id());
		model.addAttribute("title", board2.getTitle());
		model.addAttribute("id", board2.getId());
		model.addAttribute("nickname", board2.getNickname());
		model.addAttribute("contents", board2.getContents());
		model.addAttribute("viewCount", board2.getViewCount());
		model.addAttribute("date", board2.getDate());

		// 해당 게시물에 대한 댓글도 함께 가져오기
		List<Comment> comments = commentRepository.findByBoardTypeAndDetailId("board2", board2Id);
		model.addAttribute("comments", comments);

		// 목록 페이지와 동일한 방식으로 목록을 표시
		Page<board2> board2Page = board2Repository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "board2Id")));

		model.addAttribute("board2Page", board2Page);

		// 각 게시글 별 댓글 수 가져오기
		List<board2> allboard2 = board2Repository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (board2 b : allboard2) {
			long count = commentRepository.countByBoardTypeAndDetailId("board2", b.getBoard2Id());
			if (count != 0) {
				commentCounts.put(b.getBoard2Id(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "boardDetail";
	}

	@GetMapping("/search")
	public String search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			Model model, Pageable pageable) {
		Page<board2> board2Page;
		switch (category) {
		case "title":
			board2Page = board2Repository.findByTitleContainingOrderByDateDesc(keyword, pageable);
			break;
		case "contents":
			board2Page = board2Repository.findByContentsContainingOrderByDateDesc(keyword, pageable);
			break;
		case "nickname":
			board2Page = board2Repository.findByNicknameContainingOrderByDateDesc(keyword, pageable);
			break;
		default:
			board2Page = board2Repository.findAllByOrderByDateDesc(PageRequest.of(pageable.getPageNumber(), 15));
		}
		model.addAttribute("board2Page", board2Page);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);

		// 각 게시글 별 댓글 수 가져오기
		List<board2> allboard2 = board2Repository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (board2 b : allboard2) {
			long count = commentRepository.countByBoardTypeAndDetailId("board2", b.getBoard2Id());
			if (count != 0) {
				commentCounts.put(b.getBoard2Id(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "board2";
	}
}
