package Toon.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Toon.Service.teamInfoRepository;
import Toon.Entity.teamInfo;

import java.util.List;

@Controller
public class infoController {

	@Autowired
	private teamInfoRepository teamInfoRepository;

	// 프로필 페이지 출력
	@GetMapping("/profile")
	public String showProfilePage(Model model) {
		List<teamInfo> profileData = teamInfoRepository.findByCategory("profile");
		// 카테고리가 "profile"인 데이터를 모델에 추가
		model.addAttribute("profileData", profileData);
		return "profile";
	}
	
	@GetMapping("/squad")
	public String showSquadPage(Model model) {
		List<teamInfo> squadData = teamInfoRepository.findByCategory("squad");
		// 카테고리가 "profile"인 데이터를 모델에 추가
		model.addAttribute("squadData", squadData);
		return "squad";
	}
	
	@GetMapping("/stadium")
	public String showStadiumPage(Model model) {
		List<teamInfo> stadiumData = teamInfoRepository.findByCategory("stadium");
		// 카테고리가 "profile"인 데이터를 모델에 추가
		model.addAttribute("stadiumData", stadiumData);
		return "stadium";
	}
	
	@GetMapping("/manager")
	public String showManagerPage(Model model) {
		List<teamInfo> managerData = teamInfoRepository.findByCategory("manager");
		// 카테고리가 "profile"인 데이터를 모델에 추가
		model.addAttribute("managerData", managerData);
		return "manager";
	}

	@PostMapping("/editInfoform")
	public String showEditInfoForm(@RequestParam("category") String category, Model model) {
		// 여기서 카테고리를 기반으로 데이터를 조회하고 수정 페이지로 전달하는 로직을 작성하면 됩니다.
		List<teamInfo> infoList = teamInfoRepository.findByCategory(category);
		// 예를 들어, 카테고리가 유일한 경우에는 첫 번째 정보를 사용할 수 있습니다.
		if (!infoList.isEmpty()) {
			teamInfo info = infoList.get(0);
			model.addAttribute("info", info);

		}
		return "editInfoForm";
	}

	@PostMapping("/editInfo")
	public String editInfo(@RequestParam("category") String category, @RequestParam("contents") String contents) {
	    // 해당 카테고리의 정보를 가져옵니다.
	    List<teamInfo> infoList = teamInfoRepository.findByCategory(category);
	    if (!infoList.isEmpty()) {
	        teamInfo info = infoList.get(0); // 예시로 첫 번째 정보만 사용합니다.
	        // 수정된 내용을 업데이트합니다.
	        info.setContents(contents);
	        // 변경사항을 저장합니다.
	        teamInfoRepository.save(info);
	    }
	    // 수정 후에는 해당 카테고리 페이지로 리다이렉트합니다.
	    return "redirect:/"+ category;
	}


}
