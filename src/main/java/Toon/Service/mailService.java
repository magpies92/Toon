package Toon.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class mailService {

    private final JavaMailSender javaMailSender;

    private static int sendCode;  // static으로 변경

    public int sendMail(String email) {
        sendCode = createNumber();  // static 변수에 할당

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setFrom("rkdtnqls9259@gmail.com");
            helper.setTo(email);
            helper.setSubject("이메일 인증");
            String body = "<h3>툰 코리아에서 요청하신 인증 번호입니다.</h3>" + "<h1>" + sendCode + "</h1>" + "<h3>감사합니다.</h3>";
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }

        javaMailSender.send(message);

        return sendCode;
    }

    private int createNumber() {
        return (int) (Math.random() * 90000) + 100000;
    }

    // 입력받은 코드와 이전에 보낸 인증 번호를 비교하는 함수
    public boolean checkVerificationCode(int inputCode) {
        return inputCode == sendCode;
    }
}

