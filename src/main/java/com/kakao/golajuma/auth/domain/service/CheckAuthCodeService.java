package com.kakao.golajuma.auth.domain.service;

import com.kakao.golajuma.auth.web.dto.request.CheckAuthCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckAuthCodeService {
	public void execute(CheckAuthCodeRequest requestDto) {
		// 이메일과 매치되는 인증번호 찾아오기
		requestDto.getAuthCode();
	}
}
