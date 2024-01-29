package com.kakao.golajuma.auth.web.controller;

import com.kakao.golajuma.auth.domain.service.CheckAuthCodeService;
import com.kakao.golajuma.auth.domain.service.SendEmailService;
import com.kakao.golajuma.auth.web.dto.request.CheckAuthCodeRequest;
import com.kakao.golajuma.auth.web.dto.request.SendEmailRequest;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class VerifyEmailController {

	private final SendEmailService sendEmailService;

	private final CheckAuthCodeService checkAuthCodeService;

	@PostMapping("/email/verification")
	public ApiResponse<ApiResponseBody.SuccessBody<Void>> sendEmail(
			@RequestBody @Valid SendEmailRequest request) {
		sendEmailService.execute(request);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.CREATE);
	}

	@PostMapping("/email/verification/check")
	public ApiResponse<ApiResponseBody.SuccessBody<Void>> checkAuthCode(
			@RequestBody @Valid CheckAuthCodeRequest request) {
		checkAuthCodeService.execute(request);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.CREATE);
	}
}
