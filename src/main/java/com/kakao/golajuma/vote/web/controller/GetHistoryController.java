package com.kakao.golajuma.vote.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import com.kakao.golajuma.vote.domain.service.GetHistoryService;
import com.kakao.golajuma.vote.web.dto.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GetHistoryController {

	private final GetHistoryService getHistoryService;

	@GetMapping("/search/history")
	public ApiResponse<ApiResponseBody.SuccessBody<HistoryResponse>> getHistory(@Login Long userId) {
		HistoryResponse response = getHistoryService.execute(userId);
		return ApiResponseGenerator.success(response, HttpStatus.OK, MessageCode.GET);
	}
}
