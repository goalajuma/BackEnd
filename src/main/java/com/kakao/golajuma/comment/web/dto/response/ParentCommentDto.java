package com.kakao.golajuma.comment.web.dto.response;

import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParentCommentDto extends CommentDto {
	List<CommentDto> reply;

	public ParentCommentDto(
			CommentEntity commentEntity, boolean isOwner, String username, int likey) {
		super(commentEntity, isOwner, username, likey);
		this.reply = new ArrayList<>();
	}

	public void addReply(CommentDto commentDto) {
		this.reply.add(commentDto);
	}
}
