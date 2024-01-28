package com.kakao.golajuma.auth.web.controller;

import com.kakao.golajuma.auth.domain.service.GetUserProfileService;
import com.kakao.golajuma.auth.domain.service.UpdateUserNickNameService;
import com.kakao.golajuma.auth.domain.service.UpdateUserPasswordService;
import com.kakao.golajuma.auth.web.dto.request.UpdateUserNickNameRequest;
import com.kakao.golajuma.auth.web.dto.request.UpdateUserPasswordRequest;
import com.kakao.golajuma.auth.web.dto.response.UpdateNickNameResponse;
import com.kakao.golajuma.auth.web.dto.response.UserProfileResponse;
import com.kakao.golajuma.auth.web.support.Login;
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
@RequestMapping("/users")
public class UserProfileController {

	private final GetUserProfileService getUserProfileService;

	private final UpdateUserPasswordService updateUserPasswordService;

	private final UpdateUserNickNameService updateUserNickNameService;

	@GetMapping("/profile")
	public ApiResponse<ApiResponseBody.SuccessBody<UserProfileResponse>> getUserProfile(
			@Login Long userId) {

		UserProfileResponse response = getUserProfileService.execute(userId);

		return ApiResponseGenerator.success(response, HttpStatus.OK, MessageCode.GET);
	}

	@PatchMapping("/nickname")
	public ApiResponse<ApiResponseBody.SuccessBody<UpdateNickNameResponse>> updateNickName(
			@RequestBody @Valid UpdateUserNickNameRequest request, @Login Long userId) {

		UpdateNickNameResponse response = updateUserNickNameService.execute(request, userId);

		return ApiResponseGenerator.success(response, HttpStatus.OK, MessageCode.UPDATE);
	}

	@PatchMapping("/password")
	public ApiResponse<ApiResponseBody.SuccessBody<Void>> updatePassword(
			@RequestBody @Valid UpdateUserPasswordRequest request, @Login Long userId) {

		updateUserPasswordService.execute(request, userId);

		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.UPDATE);
	}
}
