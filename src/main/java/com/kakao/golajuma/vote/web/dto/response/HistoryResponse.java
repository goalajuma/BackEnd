package com.kakao.golajuma.vote.web.dto.response;

import com.kakao.golajuma.common.marker.AbstractResponseDto;
import com.kakao.golajuma.vote.persistence.entity.HistoryEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HistoryResponse implements AbstractResponseDto {
	List<Keyword> keywords;

	public HistoryResponse(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	@Getter
	@Builder
	public static class Keyword {
		private Long id;
		private String keyword;

		public static Keyword convert(Long id, String keyword) {
			return Keyword.builder().id(id).keyword(keyword).build();
		}
	}

	public static HistoryResponse convert(List<HistoryEntity> histories) {
		List<Keyword> keywords = new ArrayList<>();
		for (HistoryEntity history : histories) {
			Keyword keyword = Keyword.convert(history.getId(), history.getKeyword());
			keywords.add(keyword);
		}
		return new HistoryResponse(keywords);
	}
}
