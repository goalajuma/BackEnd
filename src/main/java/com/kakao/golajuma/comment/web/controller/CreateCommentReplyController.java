package com.kakao.golajuma.comment.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.comment.domain.service.CreateCommentService;
import com.kakao.golajuma.comment.web.dto.request.CreateCommentReplyRequest;
import com.kakao.golajuma.comment.web.dto.response.CreateCommentReplyResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments/{commentId}/reply")
public class CreateCommentReplyController {

	private final CreateCommentService createCommentService;

	@PostMapping
	public ApiResponse<ApiResponseBody.SuccessBody<CreateCommentReplyResponse>> createCommentReply(
			@PathVariable Long commentId,
			@Valid @RequestBody CreateCommentReplyRequest requestDto,
			@Login Long userId) {
		CreateCommentReplyResponse response =
				createCommentService.execute(requestDto, commentId, userId);
		return ApiResponseGenerator.success(response, HttpStatus.OK, MessageCode.CREATE);
	}
}
