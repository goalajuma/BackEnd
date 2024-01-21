package com.kakao.golajuma.comment.persistence.entity;

import static com.kakao.golajuma.comment.persistence.entity.CommentEntity.ENTITY_PREFIX;

import com.kakao.golajuma.common.BaseEntity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = LikeyEntity.ENTITY_PREFIX)
public class LikeyEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "likey";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ENTITY_PREFIX + "_id", unique = true, nullable = false)
	private Long id;

	@Column(name = ENTITY_PREFIX + "_user_id")
	private Long userId;

	@Column(name = ENTITY_PREFIX + "_comment_id")
	private Long commentId;
}
