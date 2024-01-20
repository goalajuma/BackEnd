package com.kakao.golajuma.comment.web.dto.response;

import com.kakao.golajuma.comment.persistence.entity.CommentEntity;

public class UpdateCommentResponse extends CommentDto {
	public UpdateCommentResponse(CommentEntity commentEntity, Boolean isOwner, String username, int likey) {
		super(commentEntity, isOwner, username, likey);
	}
}
