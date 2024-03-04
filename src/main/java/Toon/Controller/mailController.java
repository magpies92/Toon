package Toon.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Toon.Service.mailService;

@Controller
@RequiredArgsConstructor
public class mailController {

    private final mailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String mailSend(@RequestParam(name = "email") String email) {
        try {
            int number = mailService.sendMail(email);
            String num = "" + number;
            return num;
        } catch (Exception e) {
            // 예외 처리: 클라이언트에게 적절한 에러 메시지 반환 또는 로깅
            return "Error occurred while sending email.";
        }
    }

    @ResponseBody
    @PostMapping("/checkCode")
    public String checkVerificationCode(@RequestParam(name = "inputCode") int inputCode) {
    	try {
            if (mailService.checkVerificationCode(inputCode) == true) {
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "error";
        }
    }
    
}
