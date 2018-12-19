package net.openobject.tmmm.util;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 11.
 * @개요 : 이메일 인증에 사용할 Mail전송 기능
 */
@Component
public class MailUtil {
	@Autowired
	JavaMailSender mailSender;
	
	public void sendMail(String sender, String receiver, String title, String content) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.toString());

			messageHelper.setFrom(sender); 		// 보내는사람 생략하면 정상작동을 안함
			messageHelper.setTo(receiver); 		// 받는사람 이메일
			messageHelper.setSubject(title); 	// 메일제목은 생략이 가능하다
			messageHelper.setText(content); 		// 메일 내용

			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
