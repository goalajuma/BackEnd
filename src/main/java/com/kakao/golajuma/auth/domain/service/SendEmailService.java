package com.kakao.golajuma.auth.domain.service;

import com.kakao.golajuma.auth.domain.helper.CodeGenerator;
import com.kakao.golajuma.auth.web.supplier.EmailSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SendEmailService {

	private final ValidEmailService validEmailService;

	private final CodeGenerator codeGenerator;

	private final JavaMailSender emailSender;

	private final String TITLE = "골라주마 가입 인증번호";

	public void execute(EmailSupplier supplier) {
		// 중복검사
		validEmailService.execute(supplier);

		String email = supplier.getEmail();
		// 랜덤 인증번호 생성
		String authCode = codeGenerator.generateMixedCode();
		// 인증번호 저장 key:이메일, value:인증번호

		// 이메일 발송
		sendEmail(email, TITLE, authCode);
	}

	public void sendEmail(String receiver, String title, String text) {
		SimpleMailMessage emailForm = createEmailForm(receiver, title, text);
		emailSender.send(emailForm);
	}

	// 발신할 이메일 데이터 세팅
	private SimpleMailMessage createEmailForm(String receiver, String title, String text) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(receiver);
		message.setSubject(title);
		message.setText(text);

		return message;
	}
}
