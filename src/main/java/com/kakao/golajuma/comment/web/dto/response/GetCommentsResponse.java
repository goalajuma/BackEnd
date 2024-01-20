package com.kakao.golajuma.comment.web.dto.response;

import com.kakao.golajuma.common.marker.AbstractResponseDto;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetCommentsResponse implements AbstractResponseDto {
	private List<ParentCommentDto> comments;
}
