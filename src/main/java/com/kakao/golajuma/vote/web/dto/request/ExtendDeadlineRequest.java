package com.kakao.golajuma.vote.web.dto.request;

import com.kakao.golajuma.common.marker.AbstractRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExtendDeadlineRequest implements AbstractRequestDto {
	int timeLimit;

	public ExtendDeadlineRequest(int timeLimit) {
		this.timeLimit = timeLimit;
	}
}
