package com.jnorol.terminal.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 6. 09.
 * @수정일 : 2018. 6. 09.
 * @개요 : 이메일 인증에 사용할 Mail전송 기능
 */
@Component
public class MailUtil {
	@Autowired
	JavaMailSender mailSender;

	public static Message message = null;
	private static String HOST = "52.78.85.201";
	private static String SMTP_USERNAME = "AKIAJHFU52CXNJDUTWCQ";
	private static String SMTP_PASSWORD = "AoQo/BbPW21JBi1AOl/Mp0dmlsGO2aYrY8ZW4XiRw9Ss";
	private static Session session;
	private static Transport transport;

	public static void createMail(String sender, String receiver, String title, String content) {
		MimeBodyPart mbp = new MimeBodyPart();
		try {
			// 메일 본문 작성
			// text 경우
			mbp.setText(content);

			// message 객체에 본문을 넣기 위하여 Multipart 객체 생성
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);

			// 파일 첨부일 경우
			// MimeBodyPart mbp_file = new MimeBodyPart();
			// mbp_file.setDataHandler(new DataHandler(new FileDataSource("[보낼 파일 경로]")));
			// mbp_file.setFileName("[보낼 파일 이름]");
			// mp.addBodyPart(mbp_file);

			// html일 경우
			// MimeBodyPart mbp_html = new MimeBodyPart();
			// mbp_html.setDataHandler(new DataHandler(
			// new ByteArrayDataSource(new FileInputStream(new File("[보낼 HTML 경로]")),
			// "text/html")));
			// mp.addBodyPart(mbp_html);

			// 메일 제목 넣기
			message.setSubject(title);

			// 메일 본문을 넣기
			message.setContent(mp);

			// 보내는 날짜
			message.setSentDate(new Date());

			// 보내는 메일 주소
			message.setFrom(new InternetAddress(sender));

			// 단건 전송일 때는 사용 start
			message.setRecipient(RecipientType.TO, new InternetAddress(receiver));
			// 단건 전송일 때는 사용 end

			// 복수 건 전송일 때는 사용 start
			// InternetAddress[] receive_address = { new InternetAddress("보낼 사람의 메일 주소") };
			// message.setRecipients(RecipientType.TO, receive_address);
			// 복수 건 전송일 때는 사용 end

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void connectSMTP() {
		Properties prop = new Properties();

		// Gmail 연결을 위하여 아래 설정 적용
		// 사내 메일 망일 경우 smtp host 만 설정해도 됨 (특정 포트가 아닐경우)
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");

		// SMTP 서버 계정 정보 (GMail 용)
		MyAuthenticator authenticator = new MyAuthenticator(Constants.MAIL_SENDER, Constants.MAIL_PASSWORD);

		session = Session.getDefaultInstance(prop, authenticator);
		try {
			transport = session.getTransport();
			message = new MimeMessage(session);
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		}
	}

	public static void sendMail() {
		try {
			System.out.println("Sending...");

			// Connect to Amazon SES using the SMTP username and password you specified
			// above.
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

			// Send the email.
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		} finally {
			// Close and terminate the connection.
			try {
				transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMail(String sender, String receiver, String title, String content) {
		connectSMTP();
		createMail(sender, receiver, title, content);
		sendMail();
		// try {
		// MimeMessage message = mailSender.createMimeMessage();
		// MimeMessageHelper messageHelper = new MimeMessageHelper(message, true,
		// "UTF-8");
		//
		// messageHelper.setFrom(sender); // 보내는사람 생략하거나 하면 정상작동을 안함
		// messageHelper.setTo(receiver); // 받는사람 이메일
		// messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
		// messageHelper.setText(content); // 메일 내용
		//
		// mailSender.send(message);
		// } catch (Exception e) {
		// System.out.println(e);
		// }

	}
}

class MyAuthenticator extends Authenticator {
	private String id;
	private String pw;

	public MyAuthenticator(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(id, pw);
	}
}
