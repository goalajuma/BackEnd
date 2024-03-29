package com.kakao.golajuma.vote.web.dto.request;

import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestOptionDto {
	@Size(max = 200, message = "옵션명은 200자를 넘을 수 없습니다.")
	private String name;

	private String image;

	public RequestOptionDto(String name, String image) {
		this.name = name;
		this.image = image;
	}
}
