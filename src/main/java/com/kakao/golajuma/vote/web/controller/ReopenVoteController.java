package com.kakao.golajuma.vote.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import com.kakao.golajuma.vote.domain.service.ReopenVoteService;
import com.kakao.golajuma.vote.web.dto.request.ReopenVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReopenVoteController {

	private final ReopenVoteService reopenVoteService;

	@PatchMapping("/votes/{voteId}/reopen")
	public ApiResponse<ApiResponseBody.SuccessBody<Void>> reopen(
			@PathVariable Long voteId, @Login Long userId, @RequestBody ReopenVoteRequest requestDto) {
		reopenVoteService.execute(voteId, userId, requestDto);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.UPDATE);
	}
}
