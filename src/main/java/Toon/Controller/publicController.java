package Toon.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import Toon.Entity.notice;
import Toon.Entity.user;
import Toon.Service.UserRepository;
import Toon.Service.noticeRepository;

@ControllerAdvice
public class publicController {

    @Autowired
    private noticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void punblicElement(Model model) {
        // 다른 필요한 데이터도 모델에 추가...
        List<notice> noticeList = noticeRepository.findTop5ByOrderByNoticeIdDesc();
        List<user> userList = userRepository.findAll();
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("userList", userList); // 모든 user 엔티티를 모델에 추가
    }
}
