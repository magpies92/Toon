package Toon.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDateTime; // LocalDateTime import 추가
import java.util.List;
import java.util.Optional;

import Toon.Entity.Comment;
import Toon.Entity.board1;
import Toon.Entity.board2;
import Toon.Entity.matchday;
import Toon.Entity.news;
import Toon.Entity.notice;

import Toon.Entity.user;
import Toon.Entity.video;
import Toon.Service.UserRepository;
import Toon.Service.board1Repository;
import Toon.Service.board2Repository;
import Toon.Service.commentRepository;
import Toon.Service.matchdayRepository;
import Toon.Service.newsRepository;
import Toon.Service.noticeRepository;
import Toon.Service.videoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@Controller
@RequestMapping("/post")
public class postController {

	@Autowired
	private board1Repository board1Repository;

	@Autowired
	private board2Repository board2Repository;

	@Autowired
	private matchdayRepository matchdayRepository;

	@Autowired
	private newsRepository newsRepository;

	@Autowired
	private noticeRepository noticeRepository;

	@Autowired
	private videoRepository videoRepository;

	@Autowired
	private commentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/write")
	public String writePost(@RequestParam(name = "boardId") String boardId, @RequestParam(name = "title") String title,
			@RequestParam(name = "contents") String contents, @RequestParam(name = "image") MultipartFile image,
			RedirectAttributes redirectAttributes, HttpSession session) {
		// 이미지 파일을 파라미터로 추가
		// 세션에서 userId와 userNickname 가져오기
		String userId = (String) session.getAttribute("userId");
		String userNickname = (String) session.getAttribute("userNickname");

		// 작성 시점의 현재 시간 가져오기
		LocalDateTime currentTime = LocalDateTime.now();

		// 사용자 정보를 가져옴
		user user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// userPoint를 5 증가시킴
		int updatedUserPoint = user.getUserPoint() + 10;
		if (updatedUserPoint > 9999) {
			updatedUserPoint = 9999; // 포인트가 9999를 초과하지 않도록 설정
		}
		user.setUserPoint(updatedUserPoint);

		// 포인트에 따라 레벨 업데이트
		if (updatedUserPoint >= 2000 && user.getUserLevel() == 1) {
			user.setUserLevel(2);
		} else if (updatedUserPoint >= 3000 && user.getUserLevel() == 2) {
			user.setUserLevel(3);
		} else if (updatedUserPoint >= 4000 && user.getUserLevel() == 3) {
			user.setUserLevel(4);
		} else if (updatedUserPoint >= 5000 && user.getUserLevel() == 4) {
			user.setUserLevel(5);
		} else if (updatedUserPoint >= 6000 && user.getUserLevel() == 5) {
			user.setUserLevel(6);
		} else if (updatedUserPoint >= 7000 && user.getUserLevel() == 6) {
			user.setUserLevel(7);
		} else if (updatedUserPoint >= 8000 && user.getUserLevel() == 7) {
			user.setUserLevel(8);
		} else if (updatedUserPoint >= 9000 && user.getUserLevel() == 8) {
			user.setUserLevel(9);
		}
		// userRepository를 사용하여 업데이트된 사용자 정보 저장
		userRepository.save(user);

		// 여기에 게시글을 등록하는 로직을 추가하세요.
		// boardId, userId, userNickname, title, content, currentTime 등을 이용하여 게시글을 저장하거나
		// 처리하는 코드를 작성하세요.

		if ("board1".equals(boardId)) {
			// board1 엔티티와 연결하는 로직 추가
			board1 board1 = new board1();
			board1.setId(userId); // 수정: userId로 변경
			board1.setNickname(userNickname); // 수정: userNickname으로 변경
			board1.setTitle(title);
			board1.setContents(contents);
			board1.setDate(currentTime); // 현재 시간 저장
			// board1 엔티티에 저장
			board1Repository.save(board1);

			// 예: board1 게시판으로 리다이렉트
			return "redirect:/board1";
		} else if ("board2".equals(boardId)) {
			// board2 엔티티와 연결하는 로직 추가
			board2 board2 = new board2();
			board2.setId(userId); // 수정: userId로 변경
			board2.setNickname(userNickname); // 수정: userNickname으로 변경
			board2.setTitle(title);
			board2.setContents(contents);
			board2.setDate(currentTime); // 현재 시간 저장
			// board2 엔티티에 저장
			board2Repository.save(board2);

			// 예: board2 게시판으로 리다이렉트
			return "redirect:/board2";
		} else if ("matchday".equals(boardId)) {
			// board2 엔티티와 연결하는 로직 추가
			matchday matchday = new matchday();
			matchday.setId(userId); // 수정: userId로 변경
			matchday.setNickname(userNickname); // 수정: userNickname으로 변경
			matchday.setTitle(title);
			matchday.setContents(contents);
			matchday.setDate(currentTime); // 현재 시간 저장
			// board2 엔티티에 저장
			matchdayRepository.save(matchday);

			// 예: board2 게시판으로 리다이렉트
			return "redirect:/matchday";
		} else if ("news".equals(boardId)) {
			// board2 엔티티와 연결하는 로직 추가
			news news = new news();
			news.setId(userId); // 수정: userId로 변경
			news.setNickname(userNickname); // 수정: userNickname으로 변경
			news.setTitle(title);
			news.setContents(contents);
			news.setDate(currentTime); // 현재 시간 저장
			// board2 엔티티에 저장
			newsRepository.save(news);

			// 예: board2 게시판으로 리다이렉트
			return "redirect:/news";
		} else if ("notice".equals(boardId)) {
			// board2 엔티티와 연결하는 로직 추가
			notice notice = new notice();
			notice.setId(userId); // 수정: userId로 변경
			notice.setNickname(userNickname); // 수정: userNickname으로 변경
			notice.setTitle(title);
			notice.setContents(contents);
			notice.setDate(currentTime); // 현재 시간 저장
			// board2 엔티티에 저장
			noticeRepository.save(notice);

			// 예: board2 게시판으로 리다이렉트
			return "redirect:/notice";
		} else if ("video".equals(boardId)) {
			// board2 엔티티와 연결하는 로직 추가
			video video = new video();
			video.setId(userId); // 수정: userId로 변경
			video.setNickname(userNickname); // 수정: userNickname으로 변경
			video.setTitle(title);
			video.setContents(contents);
			video.setDate(currentTime); // 현재 시간 저장
			// board2 엔티티에 저장
			videoRepository.save(video);

			// 예: board2 게시판으로 리다이렉트
			return "redirect:/video";
		} else {
			// 기본적으로는 어떤 게시판이든 boardId를 그대로 사용하여 리다이렉트
			return "redirect:/board" + boardId;
		}
	}

	// 수정 폼 불러오기
	@PostMapping("/update")
	public String editFormPage(@RequestParam(name = "boardType") String boardType,
			@RequestParam(name = "postId") Long postId, Model model) {
		if ("board1".equals(boardType)) {
			Optional<board1> optionalBoard = board1Repository.findById(postId);

			if (optionalBoard.isPresent()) {
				board1 board1 = optionalBoard.get();

				// 모델에 수정 폼에 출력할 데이터 추가
				model.addAttribute("boardType", boardType);
				model.addAttribute("board1Id", board1.getBoard1Id());
				model.addAttribute("title", board1.getTitle());
				model.addAttribute("contents", board1.getContents());

				return "updateForm"; // 수정 폼 페이지로 이동
			}
		} else if ("board2".equals(boardType)) {
			Optional<board2> optionalBoard = board2Repository.findById(postId);

			if (optionalBoard.isPresent()) {
				board2 board2 = optionalBoard.get();

				// 모델에 수정 폼에 출력할 데이터 추가 (board2의 경우에 맞게 수정 필요)
				model.addAttribute("boardType", boardType);
				model.addAttribute("board2Id", board2.getBoard2Id());
				model.addAttribute("title", board2.getTitle());
				model.addAttribute("contents", board2.getContents());

				return "updateForm"; // 수정 폼 페이지로 이동
			}
		} else if ("news".equals(boardType)) {
			Optional<news> optionalBoard = newsRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				news news = optionalBoard.get();

				// 모델에 수정 폼에 출력할 데이터 추가 (news의 경우에 맞게 수정 필요)
				model.addAttribute("boardType", boardType);
				model.addAttribute("newsId", news.getNewsId());
				model.addAttribute("title", news.getTitle());
				model.addAttribute("contents", news.getContents());

				return "updateForm"; // 수정 폼 페이지로 이동
			}
		} else if ("notice".equals(boardType)) {
			Optional<notice> optionalBoard = noticeRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				notice notice = optionalBoard.get();

				// 모델에 수정 폼에 출력할 데이터 추가 (notice의 경우에 맞게 수정 필요)
				model.addAttribute("boardType", boardType);
				model.addAttribute("noticeId", notice.getNoticeId());
				model.addAttribute("title", notice.getTitle());
				model.addAttribute("contents", notice.getContents());

				return "updateForm"; // 수정 폼 페이지로 이동
			}
		} else if ("video".equals(boardType)) {
			Optional<video> optionalBoard = videoRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				video video = optionalBoard.get();

				// 모델에 수정 폼에 출력할 데이터 추가 (video의 경우에 맞게 수정 필요)
				model.addAttribute("boardType", boardType);
				model.addAttribute("videoId", video.getVideoId());
				model.addAttribute("title", video.getTitle());
				model.addAttribute("contents", video.getContents());

				return "updateForm"; // 수정 폼 페이지로 이동
			}
		} else if ("matchday".equals(boardType)) {
			Optional<matchday> optionalBoard = matchdayRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				matchday matchday = optionalBoard.get();

				// 모델에 수정 폼에 출력할 데이터 추가 (matchday의 경우에 맞게 수정 필요)
				model.addAttribute("boardType", boardType);
				model.addAttribute("matchdayId", matchday.getMatchdayId());
				model.addAttribute("title", matchday.getTitle());
				model.addAttribute("contents", matchday.getContents());

				return "updateForm"; // 수정 폼 페이지로 이동
			}
		}

		// 그 외의 경우는 에러 페이지로 이동
		return "redirect:/error";
	}

	// 수정

	@PostMapping("/edit")
	public String submitEditForm(@RequestParam(name = "boardType") String boardType,
	        @RequestParam(name = "postId") Long postId, @RequestParam(name = "title") String title,
	        @RequestParam(name = "contents") String contents, @RequestParam(name = "image", required = false) MultipartFile image,
	        RedirectAttributes redirectAttributes, HttpSession session) {
		if ("board1".equals(boardType)) {
			Optional<board1> optionalBoard = board1Repository.findById(postId);

			if (optionalBoard.isPresent()) {
				board1 board1 = optionalBoard.get();
				board1.setTitle(title);
				board1.setContents(contents);
				board1Repository.save(board1);
				return "redirect:/board1";
			}
		} else if ("board2".equals(boardType)) {
			Optional<board2> optionalBoard = board2Repository.findById(postId);

			if (optionalBoard.isPresent()) {
				board2 board2 = optionalBoard.get();
				board2.setTitle(title);
				board2.setContents(contents);
				board2Repository.save(board2);
				return "redirect:/board2";
			}
		} else if ("news".equals(boardType)) {
			Optional<news> optionalBoard = newsRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				news news = optionalBoard.get();
				news.setTitle(title);
				news.setContents(contents);
				newsRepository.save(news);
				return "redirect:/news";
			}
		} else if ("notice".equals(boardType)) {
			Optional<notice> optionalBoard = noticeRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				notice notice = optionalBoard.get();
				notice.setTitle(title);
				notice.setContents(contents);
				noticeRepository.save(notice);
				return "redirect:/notice";
			}
		} else if ("video".equals(boardType)) {
			Optional<video> optionalBoard = videoRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				video video = optionalBoard.get();
				video.setTitle(title);
				video.setContents(contents);
				videoRepository.save(video);
				return "redirect:/video";
			}
		} else if ("matchday".equals(boardType)) {
			Optional<matchday> optionalBoard = matchdayRepository.findById(postId);

			if (optionalBoard.isPresent()) {
				matchday matchday = optionalBoard.get();
				matchday.setTitle(title);
				matchday.setContents(contents);
				matchdayRepository.save(matchday);
				return "redirect:/matchday";
			}
		}
		return "redirect:/error";
	}

	@PostMapping("/delete")
	public String deletePost(@RequestParam(name = "boardId") String boardId, @RequestParam(name = "postId") Long postId,
			RedirectAttributes redirectAttributes) {
		// 여기에 게시글을 삭제하는 로직을 추가하세요.
		// postId를 이용하여 게시글을 찾고, 해당 게시글을 삭제하는 코드를 작성하세요.
		// boardId를 이용하여 어떤 게시판인지 구분하고, 해당 게시판의 엔터티에서 데이터를 삭제하세요.

		if ("board1".equals(boardId)) {
			// board1 엔터티에서 데이터 삭제 로직 추가
			board1Repository.deleteById(postId);
			// 예: board1 게시판으로 리다이렉트
			return "redirect:/board1";
		} else if ("board2".equals(boardId)) {
			// board2 엔터티에서 데이터 삭제 로직 추가
			board2Repository.deleteById(postId);
			// 예: board2 게시판으로 리다이렉트
			return "redirect:/board2";
		} else if ("matchday".equals(boardId)) {
			// matchday 엔터티에서 데이터 삭제 로직 추가
			matchdayRepository.deleteById(postId);
			// 예: matchday 게시판으로 리다이렉트
			return "redirect:/matchday";
		} else if ("news".equals(boardId)) {
			// news 엔터티에서 데이터 삭제 로직 추가
			newsRepository.deleteById(postId);
			// 예: news 게시판으로 리다이렉트
			return "redirect:/news";
		} else if ("notice".equals(boardId)) {
			// notice 엔터티에서 데이터 삭제 로직 추가
			noticeRepository.deleteById(postId);
			// 예: notice 게시판으로 리다이렉트
			return "redirect:/notice";
		} else if ("video".equals(boardId)) {
			// video 엔터티에서 데이터 삭제 로직 추가
			videoRepository.deleteById(postId);
			// 예: video 게시판으로 리다이렉트
			return "redirect:/video";
		} else {
			// 기본적으로는 어떤 게시판이든 boardId를 그대로 사용하여 리다이렉트
			return "redirect:/board" + boardId;
		}
	}

	@PostMapping("/addComment")
	@ResponseBody
	public String addComment(@RequestBody Comment comment) {
		// CommentService를 거치지 않고 바로 CommentRepository를 사용하여 데이터베이스에 저장
		commentRepository.save(comment);

		// 댓글의 작성자 닉네임 가져오기
		String nickname = comment.getNickname();

		// 사용자 정보를 가져옴
		user user = userRepository.findByNickname(nickname).orElseThrow(() -> new RuntimeException("User not found"));

		// userPoint를 5 증가시킴
		int updatedUserPoint = user.getUserPoint() + 5;
		if (updatedUserPoint > 9999) {
			updatedUserPoint = 9999; // 포인트가 9999를 초과하지 않도록 설정
		}
		user.setUserPoint(updatedUserPoint);

		// userRepository를 사용하여 업데이트된 사용자 정보 저장
		userRepository.save(user);

		return "댓글이 추가되었습니다.";
	}

	@GetMapping("/getComments")
	public List<Comment> getAllComments() {
		return commentRepository.findAll();
	}

	@Transactional
	@PostMapping("/updateComment")
	@ResponseBody
	public String updateComment(@RequestParam("commentId") Long commentId,
			@RequestParam("newContent") String newContent) {
		try {
			// 댓글 엔터티를 DB에서 가져옴
			Comment comment = commentRepository.findById(commentId).orElse(null);

			if (comment != null) {
				// 새로운 내용으로 댓글 엔터티 업데이트
				comment.setContents(newContent);
				commentRepository.save(comment);
				return "success";
			} else {
				// 댓글이 존재하지 않을 경우 실패 처리
				return "error";
			}
		} catch (Exception e) {
			// 업데이트 실패 시 예외 처리
			e.printStackTrace();
			return "error";
		}
	}

	@Transactional
	@PostMapping("/deleteComment")
	@ResponseBody
	public String deleteComment(@RequestParam("commentId") Long commentId) {
		try {
			// 댓글 삭제
			commentRepository.deleteById(commentId);
			return "success";
		} catch (Exception e) {
			// 삭제 실패 시 예외 처리
			e.printStackTrace();
			return "error";
		}
	}

}