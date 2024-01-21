package com.kakao.golajuma.comment.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.comment.domain.service.UpdateCommentLikeyService;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/{commentId}/likey")
public class UpdateCommentLikeyController {

	private final UpdateCommentLikeyService updateCommentLikeyService;

	@PostMapping
	public ApiResponse<ApiResponseBody.SuccessBody<Void>> updateCommentLikey(
			@PathVariable Long commentId, @Login Long userId) {
		updateCommentLikeyService.execute(commentId, userId);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.UPDATE);
	}
}
