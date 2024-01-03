package com.kakao.golajuma.comment.web.controller;

import com.kakao.golajuma.common.support.respnose.ApiResponse;
import com.kakao.golajuma.common.support.respnose.ApiResponseGenerator;
import com.kakao.golajuma.common.support.respnose.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments/{commentsId}/reply")
public class CreateCommentReplyController {

    @PostMapping
    public ApiResponse<?> createCommentReply(){
        return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.CREATE);
    }
}
