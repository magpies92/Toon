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
import Toon.Entity.news;
import Toon.Service.commentRepository;
import Toon.Service.newsRepository;

@Controller
@RequestMapping("/news")
public class newsController {

	@Autowired
	private newsRepository newsRepository;

	@Autowired
	private commentRepository commentRepository;

	@GetMapping
	public String newsList(Model model, Pageable pageable) {
		// 한 페이지에 보여줄 아이템 수를 10으로 설정하고 역순으로 정렬
		Page<news> newsPage = newsRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "newsId")));

		model.addAttribute("newsPage", newsPage);

		// 각 게시글 별 댓글 수 가져오기
		List<news> allnews = newsRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (news b : allnews) {
			long count = commentRepository.countByBoardTypeAndDetailId("news", b.getNewsId());
			if (count != 0) {
				commentCounts.put(b.getNewsId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "news";
	}

	@GetMapping("/{newsId}")
	public String newsDetail(@PathVariable(name = "newsId") Long newsId, Model model, Pageable pageable) {
		// 상세 페이지에서는 특정 게시물을 표시
		news news = newsRepository.findById(newsId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid newsId: " + newsId));

		news.setViewCount(news.getViewCount() + 1);
		newsRepository.save(news);

		model.addAttribute("boardType", "news");
		model.addAttribute("newsId", news.getNewsId());
		model.addAttribute("title", news.getTitle());
		model.addAttribute("id", news.getId());
		model.addAttribute("nickname", news.getNickname());
		model.addAttribute("contents", news.getContents());
		model.addAttribute("viewCount", news.getViewCount());
		model.addAttribute("date", news.getDate());

		// 해당 게시물에 대한 댓글도 함께 가져오기
		List<Comment> comments = commentRepository.findByBoardTypeAndDetailId("news", newsId);
		model.addAttribute("comments", comments);

		// 목록 페이지와 동일한 방식으로 목록을 표시
		Page<news> newsPage = newsRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "newsId")));

		model.addAttribute("newsPage", newsPage);

		// 각 게시글 별 댓글 수 가져오기
		List<news> allnews = newsRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (news b : allnews) {
			long count = commentRepository.countByBoardTypeAndDetailId("news", b.getNewsId());
			if (count != 0) {
				commentCounts.put(b.getNewsId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "boardDetail";
	}

	@GetMapping("/search")
	public String search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			Model model, Pageable pageable) {
		Page<news> newsPage;
		switch (category) {
		case "title":
			newsPage = newsRepository.findByTitleContainingOrderByDateDesc(keyword, pageable);
			break;
		case "contents":
			newsPage = newsRepository.findByContentsContainingOrderByDateDesc(keyword, pageable);
			break;
		case "nickname":
			newsPage = newsRepository.findByNicknameContainingOrderByDateDesc(keyword, pageable);
			break;
		default:
			newsPage = newsRepository.findAllByOrderByDateDesc(PageRequest.of(pageable.getPageNumber(), 15));

		}
		model.addAttribute("newsPage", newsPage);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);

		// 각 게시글 별 댓글 수 가져오기
		List<news> allnews = newsRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (news b : allnews) {
			long count = commentRepository.countByBoardTypeAndDetailId("news", b.getNewsId());
			if (count != 0) {
				commentCounts.put(b.getNewsId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "news";
	}

}
