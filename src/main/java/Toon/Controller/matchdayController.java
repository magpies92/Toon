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
import Toon.Entity.matchday;
import Toon.Service.commentRepository;
import Toon.Service.matchdayRepository;

@Controller
@RequestMapping("/matchday")
public class matchdayController {

	@Autowired
	private matchdayRepository matchdayRepository;

	@Autowired
	private commentRepository commentRepository;

	@GetMapping
	public String matchdayList(Model model, Pageable pageable) {
		// 한 페이지에 보여줄 아이템 수를 10으로 설정하고 역순으로 정렬
		Page<matchday> matchdayPage = matchdayRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "matchdayId")));

		model.addAttribute("matchdayPage", matchdayPage);

		// 각 게시글 별 댓글 수 가져오기
		List<matchday> allmatchday = matchdayRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (matchday b : allmatchday) {
			long count = commentRepository.countByBoardTypeAndDetailId("matchday", b.getMatchdayId());
			if (count != 0) {
				commentCounts.put(b.getMatchdayId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "matchday";
	}

	@GetMapping("/{matchdayId}")
	public String matchdayDetail(@PathVariable(name = "matchdayId") Long matchdayId, Model model, Pageable pageable) {
		// 상세 페이지에서는 특정 게시물을 표시
		matchday matchday = matchdayRepository.findById(matchdayId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid matchdayId: " + matchdayId));

		matchday.setViewCount(matchday.getViewCount() + 1);
		matchdayRepository.save(matchday);

		model.addAttribute("boardType", "matchday");
		model.addAttribute("matchdayId", matchday.getMatchdayId());
		model.addAttribute("title", matchday.getTitle());
		model.addAttribute("id", matchday.getId());
		model.addAttribute("nickname", matchday.getNickname());
		model.addAttribute("contents", matchday.getContents());
		model.addAttribute("viewCount", matchday.getViewCount());
		model.addAttribute("date", matchday.getDate());

		// 해당 게시물에 대한 댓글도 함께 가져오기
		List<Comment> comments = commentRepository.findByBoardTypeAndDetailId("matchday", matchdayId);
		model.addAttribute("comments", comments);

		// 목록 페이지와 동일한 방식으로 목록을 표시
		Page<matchday> matchdayPage = matchdayRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "matchdayId")));

		model.addAttribute("matchdayPage", matchdayPage);

		// 각 게시글 별 댓글 수 가져오기
		List<matchday> allmatchday = matchdayRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (matchday b : allmatchday) {
			long count = commentRepository.countByBoardTypeAndDetailId("matchday", b.getMatchdayId());
			if (count != 0) {
				commentCounts.put(b.getMatchdayId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "boardDetail";
	}

	@GetMapping("/search")
	public String search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			Model model, Pageable pageable) {
		Page<matchday> matchdayPage;
		switch (category) {
		case "title":
			matchdayPage = matchdayRepository.findByTitleContainingOrderByDateDesc(keyword, pageable);
			break;
		case "contents":
			matchdayPage = matchdayRepository.findByContentsContainingOrderByDateDesc(keyword, pageable);
			break;
		case "nickname":
			matchdayPage = matchdayRepository.findByNicknameContainingOrderByDateDesc(keyword, pageable);
			break;
		default:
			matchdayPage = matchdayRepository.findAllByOrderByDateDesc(PageRequest.of(pageable.getPageNumber(), 15));
		}
		model.addAttribute("matchdayPage", matchdayPage);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);

		// 각 게시글 별 댓글 수 가져오기
		List<matchday> allmatchday = matchdayRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (matchday b : allmatchday) {
			long count = commentRepository.countByBoardTypeAndDetailId("matchday", b.getMatchdayId());
			if (count != 0) {
				commentCounts.put(b.getMatchdayId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "matchday";
	}
}