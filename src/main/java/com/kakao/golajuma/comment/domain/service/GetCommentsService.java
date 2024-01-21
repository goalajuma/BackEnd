package com.kakao.golajuma.comment.domain.service;

import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import com.kakao.golajuma.comment.persistence.repository.CommentRepository;
import com.kakao.golajuma.comment.persistence.repository.LikeyRepository;
import com.kakao.golajuma.comment.web.dto.response.CommentDto;
import com.kakao.golajuma.comment.web.dto.response.GetCommentsResponse;
import com.kakao.golajuma.comment.web.dto.response.ParentCommentDto;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GetCommentsService {

	private final CommentRepository commentRepository;

	private final LikeyRepository likeyRepository;

	private final GetUserNameService getUserNameService;

	private static final String DELETED_COMMENT_CONTENT = "삭제된 댓글입니다.";

	public GetCommentsResponse execute(Long voteId, Long userId) {

		List<CommentEntity> commentEntities = commentRepository.findByVoteId(voteId);

		List<ParentCommentDto> getCommentDtos = commentDtosConverter(commentEntities, userId);

		return new GetCommentsResponse(getCommentDtos);
	}

	private List<ParentCommentDto> commentDtosConverter(
			List<CommentEntity> commentEntities, Long userId) {
		Map<Long, String[]> usernameMap = getUserNameService.execute(commentEntities);

		Map<Boolean, List<CommentEntity>> partitionedEntities =
				commentEntities.stream().collect(Collectors.partitioningBy(CommentEntity::isParent));

		List<CommentEntity> parentEntities = partitionedEntities.get(true);
		List<CommentEntity> childEntities = partitionedEntities.get(false);

		List<ParentCommentDto> parentCommentDtos =
				parentEntities.stream()
						.map(parentEntity -> toParentCommentDto(userId, parentEntity, usernameMap))
						.collect(Collectors.toList());

		for (CommentEntity childEntity : childEntities) {
			parentCommentDtos.stream()
					.filter(
							parentCommentDto ->
									Objects.equals(parentCommentDto.getId(), childEntity.getParentId())
											&& !childEntity.isDeleted())
					.findFirst()
					.ifPresent(
							parentCommentDto ->
									parentCommentDto.addReply(toChildCommentDto(userId, childEntity, usernameMap)));
		}

		parentCommentDtos.removeIf(
				parentCommentDto ->
						Objects.equals(parentCommentDto.getContent(), DELETED_COMMENT_CONTENT)
								&& parentCommentDto.getReply().isEmpty());

		return parentCommentDtos;
	}

	private String getUsernameFromMap(CommentEntity commentEntity, Map<Long, String[]> usernameMap) {
		if (commentEntity.isAnonymous()) return usernameMap.get(commentEntity.getUserId())[1];
		return usernameMap.get(commentEntity.getUserId())[0];
	}

	private CommentDto toCommentDto(
			Long userId, CommentEntity commentEntity, Map<Long, String[]> usernameMap, boolean isParent) {
		String username = getUsernameFromMap(commentEntity, usernameMap);
		boolean isOwner = commentEntity.isOwner(userId);
		int likey = likeyRepository.countByCommentId(commentEntity.getId());

		if (isParent) {
			if (Boolean.TRUE.equals(commentEntity.isDeleted()))
				commentEntity.updateContent(DELETED_COMMENT_CONTENT);
			return new ParentCommentDto(commentEntity, isOwner, username, likey);
		} else {
			return new CommentDto(commentEntity, isOwner, username, likey);
		}
	}

	public CommentDto toChildCommentDto(
			Long userId, CommentEntity commentEntity, Map<Long, String[]> usernameMap) {
		return toCommentDto(userId, commentEntity, usernameMap, false);
	}

	public ParentCommentDto toParentCommentDto(
			Long userId, CommentEntity commentEntity, Map<Long, String[]> usernameMap) {
		return (ParentCommentDto)
				toCommentDto(
						userId, commentEntity, usernameMap, Boolean.TRUE.equals(commentEntity.isParent()));
	}
}
