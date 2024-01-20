package com.kakao.golajuma.comment.persistence.entity;

import static com.kakao.golajuma.comment.persistence.entity.CommentEntity.ENTITY_PREFIX;

import com.kakao.golajuma.common.BaseEntity;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = CommentEntity.ENTITY_PREFIX)
public class CommentEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "comment";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ENTITY_PREFIX + "_id", unique = true, nullable = false)
	private Long id;

	@Column(name = ENTITY_PREFIX + "_vote_id", length = 15, nullable = false)
	private Long voteId;

	@Column(name = ENTITY_PREFIX + "_user_id", length = 15, nullable = false)
	private Long userId;

	@Column(name = ENTITY_PREFIX + "_content", length = 255, nullable = false)
	private String content;

	@Column(name = ENTITY_PREFIX + "_parent_id")
	private Long parentId;

	@Column(name = ENTITY_PREFIX + "_anonymous", nullable = false)
	private boolean anonymous;

	public void updateContent(String content) {
		this.content = content;
	}

	public boolean isOwner(Long userId) {
		return this.userId.equals(userId);
	}

	public boolean isParent(){
        return Objects.isNull(this.parentId);
    }

	public boolean isDeleted(){
		return this.getDeleted();
	}
}
