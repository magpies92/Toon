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
import Toon.Entity.board1;
import Toon.Service.board1Repository;
import Toon.Service.commentRepository;

@Controller
@RequestMapping("/board1")
public class board1Controller {

	@Autowired
	private board1Repository board1Repository;

	@Autowired
	private commentRepository commentRepository;

	@GetMapping
	public String board1List(Model model, Pageable pageable) {
		// 한 페이지에 보여줄 아이템 수를 10으로 설정하고 역순으로 정렬
		Page<board1> board1Page = board1Repository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "board1Id")));

		model.addAttribute("board1Page", board1Page);

		// 각 게시글 별 댓글 수 가져오기
		List<board1> allBoard1 = board1Repository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (board1 b : allBoard1) {
			long count = commentRepository.countByBoardTypeAndDetailId("board1", b.getBoard1Id());
			if (count != 0) {
				commentCounts.put(b.getBoard1Id(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);
		return "board1";
	}

	@GetMapping("/{board1Id}")
	public String board1Detail(@PathVariable(name = "board1Id") Long board1Id, Model model, Pageable pageable) {
		// 상세 페이지에서는 특정 게시물을 표시
		board1 board1 = board1Repository.findById(board1Id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid board1Id: " + board1Id));

		board1.setViewCount(board1.getViewCount() + 1);
		board1Repository.save(board1);

		model.addAttribute("boardType", "board1");
		model.addAttribute("board1Id", board1.getBoard1Id());
		model.addAttribute("title", board1.getTitle());
		model.addAttribute("id", board1.getId());
		model.addAttribute("nickname", board1.getNickname());
		model.addAttribute("contents", board1.getContents());
		model.addAttribute("viewCount", board1.getViewCount());
		model.addAttribute("date", board1.getDate());

		// 해당 게시물에 대한 댓글도 함께 가져오기
		List<Comment> comments = commentRepository.findByBoardTypeAndDetailId("board1", board1Id);
		model.addAttribute("comments", comments);

		// 목록 페이지와 동일한 방식으로 목록을 표시
		Page<board1> board1Page = board1Repository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "board1Id")));

		model.addAttribute("board1Page", board1Page);

		// 각 게시글 별 댓글 수 가져오기
		List<board1> allBoard1 = board1Repository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (board1 b : allBoard1) {
			long count = commentRepository.countByBoardTypeAndDetailId("board1", b.getBoard1Id());
			if (count != 0) {
				commentCounts.put(b.getBoard1Id(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "boardDetail";
	}

	@GetMapping("/search")
	public String search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			Model model, Pageable pageable) {
		Page<board1> board1Page;
		switch (category) {
		case "title":
			board1Page = board1Repository.findByTitleContainingOrderByDateDesc(keyword, pageable);
			break;
		case "contents":
			board1Page = board1Repository.findByContentsContainingOrderByDateDesc(keyword, pageable);
			break;
		case "nickname":
			board1Page = board1Repository.findByNicknameContainingOrderByDateDesc(keyword, pageable);
			break;

		default:
			board1Page = board1Repository.findAllByOrderByDateDesc(PageRequest.of(pageable.getPageNumber(), 15));

		}
		model.addAttribute("board1Page", board1Page);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);

		// 각 게시글 별 댓글 수 가져오기
		List<board1> allBoard1 = board1Repository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (board1 b : allBoard1) {
			long count = commentRepository.countByBoardTypeAndDetailId("board1", b.getBoard1Id());
			if (count != 0) {
				commentCounts.put(b.getBoard1Id(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "board1";
	}

}
