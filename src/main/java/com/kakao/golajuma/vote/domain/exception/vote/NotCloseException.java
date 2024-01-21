package com.kakao.golajuma.vote.domain.exception.vote;

import com.kakao.golajuma.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class NotCloseException extends BusinessException {
	private static final String MESSAGE = "아직 진행중인 투표입니다.";

	public NotCloseException() {
		super(MESSAGE, HttpStatus.BAD_REQUEST);
	}
}
