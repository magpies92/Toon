package Toon.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class mappingController {

    @RequestMapping("/main")
    public String mainPage(Model model) {
        return "main";
  
    }  
    @RequestMapping("/join")
    public String joinPage(Model model) {
        return "join";
  
    }
    @RequestMapping("/idpwSearch")
    public String idpwSearchPage(Model model) {
        return "idpwSearch";
  
    }
    @RequestMapping("/notice")
    public String noticePage(Model model) {
    	return "notice";
    	
    }
    @RequestMapping("/news")
    public String newsPage(Model model) {
    	return "news";
    	
    }
    @RequestMapping("/video")
    public String videoPage(Model model) {
    	return "video";
    	
    }
    @RequestMapping("/board1")
    public String board1Page(Model model) {
    	return "board1";
    	
    }
    @RequestMapping("/board2")
    public String board2Page(Model model) {
    	return "board2";
    	
    }
    @RequestMapping("/matchday")
    public String matchdayPage(Model model) {
    	return "matchday";
    	
    }
    @RequestMapping("/profile")
    public String profilePage(Model model) {
    	return "profile";
    	
    }
    @RequestMapping("/stadium")
    public String stadiumPage(Model model) {
    	return "stadium";
    	
    }
    @RequestMapping("/manager")
    public String managerPage(Model model) {
    	return "manager";
    	
    }
    @RequestMapping("/squad")
    public String squadPage(Model model) {
    	return "squad";
    	
    }
    @RequestMapping("/admin")
    public String adminPage(Model model) {
    	return "admin";
    	
    }
    @RequestMapping("/punish")
    public String punishPage(Model model) {
    	return "punish";
    	
    }
    @RequestMapping("/about")
    public String aboutPage(Model model) {
    	return "about";
    	
    }
    @RequestMapping("/mypage")
    public String mypage(Model model) {
    	return "mypage";
    	
    } 
    @RequestMapping("/write")
    public String write(Model model) {
    	return "write";
    	
    }
    @RequestMapping("/editForm")
    public String update(Model model) {
    	return "editForm";
    	
    }
    @RequestMapping("/msg")
    public String test(Model model) {
    	return "msg";
    	
    }

    @RequestMapping("/passUpdate")
    public String passUpdate(Model model) {
    	return "passUpdate";
    	
    }
}
