package com.kakao.golajuma.vote.persistence.entity;

import com.kakao.golajuma.common.BaseEntity;
import com.kakao.golajuma.vote.util.ImageUploader;
import com.kakao.golajuma.vote.web.dto.request.RequestOptionDto;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@ToString
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vote_" + OptionEntity.ENTITY_PREFIX)
public class OptionEntity extends BaseEntity {
	public static final String ENTITY_PREFIX = "option";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ENTITY_PREFIX + "_id", nullable = false)
	private Long id;

	@Column(name = ENTITY_PREFIX + "_vote_id", nullable = false)
	private Long voteId;

	@Column(name = ENTITY_PREFIX + "_name", nullable = false, length = 200)
	private String optionName;

	@Column(name = ENTITY_PREFIX + "_image")
	private String optionImage;

	@Column(name = ENTITY_PREFIX + "_count")
	@ColumnDefault("0")
	private int optionCount;

	public static OptionEntity create(
			ImageUploader imageUploader, RequestOptionDto optionDto, Long voteId) {
		if (optionDto.getImage() != null) {
			String imagePath = imageUploader.uploadImageByBase64(optionDto);
			return OptionEntity.createWithImage(optionDto, imagePath, voteId);
		}
		return OptionEntity.createWithoutImage(optionDto, voteId);
	}

	public static OptionEntity createWithoutImage(RequestOptionDto request, Long voteId) {
		return OptionEntity.builder()
				.voteId(voteId)
				.optionName(request.getName())
				.optionImage(null)
				.build();
	}

	public static OptionEntity createWithImage(
			RequestOptionDto request, String imagePath, Long voteId) {
		return OptionEntity.builder()
				.voteId(voteId)
				.optionName(request.getName())
				.optionImage(imagePath)
				.build();
	}

	public void updateCount() {
		this.optionCount += 1;
	}

	public void decreaseCount() {
		this.optionCount -= 1;
	}
}
