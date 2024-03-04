package Toon.Controller;

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

import Toon.Entity.notice;
import Toon.Service.noticeRepository;

@Controller
@RequestMapping("/notice")
public class noticeController {

	@Autowired
	private noticeRepository noticeRepository;
	

	@GetMapping
	public String noticeList(Model model, Pageable pageable) {
		// 한 페이지에 보여줄 아이템 수를 10으로 설정하고 역순으로 정렬
		Page<notice> noticePage = noticeRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "noticeId")));

		model.addAttribute("noticePage", noticePage);
		return "notice";
	}

	@GetMapping("/{noticeId}")
	public String noticeDetail(@PathVariable(name = "noticeId") Long noticeId, Model model, Pageable pageable) {
		// 상세 페이지에서는 특정 게시물을 표시
		notice notice = noticeRepository.findById(noticeId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid noticeId: " + noticeId));
		
		notice.setViewCount(notice.getViewCount() + 1);
	    noticeRepository.save(notice);

		model.addAttribute("boardType", "notice");
		model.addAttribute("noticeId", notice.getNoticeId());
		model.addAttribute("title", notice.getTitle());
		model.addAttribute("id", notice.getId());
		model.addAttribute("nickname", notice.getNickname());
		model.addAttribute("contents", notice.getContents());
		model.addAttribute("viewCount", notice.getViewCount());
		model.addAttribute("date", notice.getDate());
		
		// 목록 페이지와 동일한 방식으로 목록을 표시
		Page<notice> noticePage = noticeRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "noticeId")));

		model.addAttribute("noticePage", noticePage);

		return "boardDetail";
	}

	@GetMapping("/search")
	public String search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			Model model, Pageable pageable) {
		Page<notice> noticePage;
		switch (category) {
		case "title":
			noticePage = noticeRepository.findByTitleContainingOrderByDateDesc(keyword, pageable);
			break;
		case "contents":
			noticePage = noticeRepository.findByContentsContainingOrderByDateDesc(keyword, pageable);
			break;
		case "nickname":
			noticePage = noticeRepository.findByNicknameContainingOrderByDateDesc(keyword, pageable);
			break;
		default:
			noticePage = noticeRepository.findAllByOrderByDateDesc(PageRequest.of(pageable.getPageNumber(), 15));

		}
		model.addAttribute("noticePage", noticePage);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);
		return "notice";
	}
}