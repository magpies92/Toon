package Toon.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Toon.Entity.board1;
import Toon.Entity.board2;
import Toon.Entity.news;
import Toon.Entity.notice;

import Toon.Service.board1Repository;
import Toon.Service.board2Repository;
import Toon.Service.commentRepository;
import Toon.Service.newsRepository;
import Toon.Service.noticeRepository;

@Controller
@RequestMapping("/main")
public class mainController {

	@Autowired
	private board1Repository board1Repository;
	@Autowired
	private board2Repository board2Repository;
	@Autowired
	private newsRepository newsRepository;
	@Autowired
	private noticeRepository noticeRepository;
	@Autowired
	private commentRepository commentRepository;

	@GetMapping
	public String main(Model model) {
		// board1 목록을 가져와서 모델에 추가
		List<board1> board1List = board1Repository.findTop8ByOrderByBoard1IdDesc();
		model.addAttribute("board1List", board1List);

		// 각 게시글 별 댓글 수 가져오기
		List<board1> allboard1 = board1Repository.findAll();
		Map<Long, Long> commentCounts1 = new HashMap<>();
		for (board1 b : allboard1) {
			long count = commentRepository.countByBoardTypeAndDetailId("board1", b.getBoard1Id());
			if (count != 0) {
				commentCounts1.put(b.getBoard1Id(), count);
			}
		}
		model.addAttribute("commentCounts1", commentCounts1);

		// 다른 필요한 데이터도 모델에 추가...
		List<board2> board2List = board2Repository.findTop8ByOrderByBoard2IdDesc();
		model.addAttribute("board2List", board2List);

		// 각 게시글 별 댓글 수 가져오기
		List<board2> allboard2 = board2Repository.findAll();
		Map<Long, Long> commentCounts2 = new HashMap<>();
		for (board2 b : allboard2) {
			long count = commentRepository.countByBoardTypeAndDetailId("board2", b.getBoard2Id());
			if (count != 0) {
				commentCounts2.put(b.getBoard2Id(), count);
			}
		}
		model.addAttribute("commentCounts2", commentCounts2);

		// 다른 필요한 데이터도 모델에 추가...
		List<news> newsList = newsRepository.findTop7ByOrderByNewsIdDesc();
		model.addAttribute("newsList", newsList);

		// 각 게시글 별 댓글 수 가져오기
		List<news> allnews = newsRepository.findAll();
		Map<Long, Long> commentCounts3 = new HashMap<>();
		for (news b : allnews) {
			long count = commentRepository.countByBoardTypeAndDetailId("news", b.getNewsId());
			if (count != 0) {
				commentCounts3.put(b.getNewsId(), count);
			}
		}
		model.addAttribute("commentCounts3", commentCounts3);

		// 다른 필요한 데이터도 모델에 추가...
		List<notice> noticeList = noticeRepository.findTop5ByOrderByNoticeIdDesc();
		model.addAttribute("noticeList", noticeList);

		return "main"; // main.html을 반환
	}
}
