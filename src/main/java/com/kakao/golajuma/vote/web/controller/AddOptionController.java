package com.kakao.golajuma.vote.web.controller;

import com.kakao.golajuma.auth.web.support.Login;
import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseBody;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import com.kakao.golajuma.vote.domain.service.AddOptionService;
import com.kakao.golajuma.vote.web.dto.request.AddOptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AddOptionController {

    private final AddOptionService addOptionService;

    @PostMapping("/votes/{voteId}/options")
    public ApiResponse<ApiResponseBody.SuccessBody<Void>> addOption(
            @RequestBody @Valid AddOptionRequest requestDto, @PathVariable Long voteId, @Login Long userId) {
        addOptionService.execute(requestDto, voteId, userId);
        return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.CREATE);
    }
}
