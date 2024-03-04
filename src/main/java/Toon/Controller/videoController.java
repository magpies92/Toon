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
import Toon.Entity.video;
import Toon.Service.commentRepository;
import Toon.Service.videoRepository;

@Controller
@RequestMapping("/video")
public class videoController {

	@Autowired
	private videoRepository videoRepository;

	@Autowired
	private commentRepository commentRepository;

	@GetMapping
	public String videoList(Model model, Pageable pageable) {
		// 한 페이지에 보여줄 아이템 수를 10으로 설정하고 역순으로 정렬
		Page<video> videoPage = videoRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "videoId")));

		model.addAttribute("videoPage", videoPage);

		// 각 게시글 별 댓글 수 가져오기
		List<video> allvideo = videoRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (video b : allvideo) {
			long count = commentRepository.countByBoardTypeAndDetailId("video", b.getVideoId());
			if (count != 0) {
				commentCounts.put(b.getVideoId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "video";
	}

	@GetMapping("/{videoId}")
	public String videoDetail(@PathVariable(name = "videoId") Long videoId, Model model, Pageable pageable) {
		// 상세 페이지에서는 특정 게시물을 표시
		video video = videoRepository.findById(videoId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid videoId: " + videoId));

		video.setViewCount(video.getViewCount() + 1);
		videoRepository.save(video);

		model.addAttribute("boardType", "video");
		model.addAttribute("videoId", video.getVideoId());
		model.addAttribute("title", video.getTitle());
		model.addAttribute("id", video.getId());
		model.addAttribute("nickname", video.getNickname());
		model.addAttribute("contents", video.getContents());
		model.addAttribute("viewCount", video.getViewCount());
		model.addAttribute("date", video.getDate());

		// 해당 게시물에 대한 댓글도 함께 가져오기
		List<Comment> comments = commentRepository.findByBoardTypeAndDetailId("video", videoId);
		model.addAttribute("comments", comments);

		// 목록 페이지와 동일한 방식으로 목록을 표시
		Page<video> videoPage = videoRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "videoId")));

		model.addAttribute("videoPage", videoPage);

		// 각 게시글 별 댓글 수 가져오기
		List<video> allvideo = videoRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (video b : allvideo) {
			long count = commentRepository.countByBoardTypeAndDetailId("video", b.getVideoId());
			if (count != 0) {
				commentCounts.put(b.getVideoId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "boardDetail";
	}

	@GetMapping("/search")
	public String search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			Model model, Pageable pageable) {
		Page<video> videoPage;
		switch (category) {
		case "title":
			videoPage = videoRepository.findByTitleContainingOrderByDateDesc(keyword, pageable);
			break;
		case "contents":
			videoPage = videoRepository.findByContentsContainingOrderByDateDesc(keyword, pageable);
			break;
		case "nickname":
			videoPage = videoRepository.findByNicknameContainingOrderByDateDesc(keyword, pageable);
			break;
		default:
			videoPage = videoRepository.findAllByOrderByDateDesc(PageRequest.of(pageable.getPageNumber(), 15));

		}
		model.addAttribute("videoPage", videoPage);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);

		// 각 게시글 별 댓글 수 가져오기
		List<video> allvideo = videoRepository.findAll();
		Map<Long, Long> commentCounts = new HashMap<>();
		for (video b : allvideo) {
			long count = commentRepository.countByBoardTypeAndDetailId("video", b.getVideoId());
			if (count != 0) {
				commentCounts.put(b.getVideoId(), count);
			}
		}
		model.addAttribute("commentCounts", commentCounts);

		return "video";
	}
}