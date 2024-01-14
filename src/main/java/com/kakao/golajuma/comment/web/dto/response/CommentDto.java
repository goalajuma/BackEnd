package com.kakao.golajuma.comment.web.dto.response;

import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import com.kakao.golajuma.common.marker.AbstractResponseDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentDto implements AbstractResponseDto {
	private Long id;
	private Boolean isOwner;
	private String username;
	private String content;
	private LocalDateTime createTime;
	private int likey;

	public CommentDto(CommentEntity entity, Boolean isOwner, String username, int likey) {
		this.id = entity.getId();
		this.isOwner = isOwner;
		this.username = username;
		this.content = entity.getContent();
		this.createTime = entity.getUpdatedDate();
		this.likey = likey;
	}
}
