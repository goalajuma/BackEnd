package com.kakao.golajuma.vote.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import com.kakao.golajuma.vote.domain.service.ExtendDeadlineService;
import com.kakao.golajuma.vote.web.dto.request.ExtendDeadlineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExtendDeadlineController {
    private final ExtendDeadlineService extendDeadlineService;

    @PatchMapping("/votes/{voteId}/deadline")
    public ApiResponse<ApiResponseBody.SuccessBody<Void>> deadline(
            @PathVariable Long voteId, @Login Long userId, @RequestBody ExtendDeadlineRequest requestDto) {
        extendDeadlineService.execute(voteId, userId, requestDto);
        return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.UPDATE);
    }
}
