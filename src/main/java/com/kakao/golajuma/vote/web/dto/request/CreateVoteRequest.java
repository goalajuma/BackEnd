package com.kakao.golajuma.vote.web.dto.request;

import com.kakao.golajuma.common.marker.AbstractRequestDto;
import com.kakao.golajuma.vote.util.OptionCheck;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class CreateVoteRequest implements AbstractRequestDto {
	@NotBlank(message = "투표 제목은 필수입니다.")
	@Size(max = 256, message = "투표 제목은 256자를 넘을 수 없습니다.")
	private String title;

	@Size(max = 1000, message = "투표 내용은 1000자를 넘을 수 없습니다.")
	private String content;

	@OptionCheck List<OptionDto> options;

	private String category;
	private int timeLimit;
	private boolean voteAnonymous;

	public CreateVoteRequest(
			String voteTitle,
			String category,
			String voteContent,
			int timeLimit,
			boolean voteAnonymous,
			List<OptionDto> options) {
		this.title = voteTitle;
		this.category = category;
		this.content = voteContent;
		this.timeLimit = timeLimit;
		this.voteAnonymous = voteAnonymous;
		this.options = options;
	}

	@Getter
	public static class OptionDto extends RequestOptionDto{
		public OptionDto(String name, String image){
			super(name, image);
		}
		public OptionDto(String name){
			super(name, null);
		}
	}
}
