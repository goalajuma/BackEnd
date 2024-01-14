package com.kakao.golajuma.comment.web.dto.response;

import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
public class ParentCommentDto extends CommentDto{
    List<CommentDto> reply;

    public ParentCommentDto(CommentEntity commentEntity, boolean isOwner, String username, int likey) {
        super(commentEntity, isOwner, username, likey);
        this.reply = new ArrayList<>();
    }

    public void addReply(CommentEntity commentEntity, boolean isOwner, String username, int likey){
        this.reply.add(new CommentDto(commentEntity, isOwner, username, likey));
    }
}