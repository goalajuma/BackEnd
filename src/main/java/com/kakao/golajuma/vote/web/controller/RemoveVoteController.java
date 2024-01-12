package com.kakao.golajuma.vote.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import com.kakao.golajuma.vote.domain.service.RemoveVoteService;
import com.kakao.golajuma.vote.web.dto.request.ReopenVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RemoveVoteController {
    private final RemoveVoteService removeVoteService;

    @DeleteMapping("/votes/{voteId}")
    public ApiResponse<ApiResponseBody.SuccessBody<Void>> delete(
            @PathVariable Long voteId, @Login Long userId) {
        removeVoteService.execute(voteId, userId);
        return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.DELETE);
    }

}
