package com.kakao.golajuma.vote.web.dto.request;

import com.kakao.golajuma.common.marker.AbstractRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@ToString
@Getter
@Builder
public class AddOptionRequest implements AbstractRequestDto {
	List<RequestOptionDto> options;

	public AddOptionRequest() {
		this.options = new ArrayList<>();
	}

	@Getter
	public static class OptionDto extends RequestOptionDto {
		public OptionDto(String name, String image) {
			super(name, image);
		}

		public OptionDto(String name) {
			super(name, null);
		}
	}
}
