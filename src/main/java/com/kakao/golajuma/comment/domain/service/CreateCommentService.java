package com.kakao.golajuma.comment.domain.service;

import com.kakao.golajuma.comment.domain.exception.NotFoundCommentException;
import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import com.kakao.golajuma.comment.persistence.repository.CommentRepository;
import com.kakao.golajuma.comment.web.dto.request.CreateCommentReplyRequest;
import com.kakao.golajuma.comment.web.dto.request.CreateCommentRequest;
import com.kakao.golajuma.comment.web.dto.response.CreateCommentReplyResponse;
import com.kakao.golajuma.comment.web.dto.response.CreateCommentResponse;
import com.kakao.golajuma.vote.domain.exception.decision.NotFoundDecisionVoteException;
import com.kakao.golajuma.vote.domain.exception.vote.NotFoundVoteException;
import com.kakao.golajuma.vote.persistence.entity.OptionEntity;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.DecisionRepository;
import com.kakao.golajuma.vote.persistence.repository.OptionRepository;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import java.util.List;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CreateCommentService {

	private final CommentRepository commentRepository;

	private final GetUserNameService getUserNameService;

	private final DecisionRepository decisionRepository;

	private final OptionRepository optionRepository;

	private final VoteRepository voteRepository;

	/**
	 * 댓글을 생성한다.
	 *
	 * @param requestDto 생성할 댓글을 포함하고 있음
	 * @param voteId 투표 식별자
	 * @param userId 유저 식별자
	 * @return 생성한 댓글의 정보
	 */
	public CreateCommentResponse execute(CreateCommentRequest requestDto, Long voteId, Long userId) {
		VoteEntity voteEntity = voteRepository.findById(voteId).orElseThrow(NotFoundVoteException::new);

		// 완료된 투표가 아니고, 주인도 아니고, 투표도 안했으면 오류발생. 주인이거나 완료된 투표거나, 투표했거나 셋 중 하나만 만족해도 문제없음
		if (!voteEntity.isComplete()
				&& !voteEntity.isOwner(userId)
				&& existDecisionByVote(userId, voteId)) {
			throw new NotFoundDecisionVoteException();
		}

		CommentEntity commentEntity = saveComment(requestDto.toEntity(voteId, userId, null));

		String username = getUserNameService.execute(commentEntity);

		return new CreateCommentResponse(commentEntity, true, username, 0);
	}

	public CreateCommentReplyResponse execute(
			CreateCommentReplyRequest requestDto, Long commentId, Long userId) {
		// 부모 댓글을 검색하고 부모 댓글이 존재한다면 해당 댓글 저장
		CommentEntity parentCommentEntity =
				commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);

		VoteEntity voteEntity =
				voteRepository
						.findById(parentCommentEntity.getVoteId())
						.orElseThrow(NotFoundVoteException::new);

		// 완료된 투표가 아니고, 주인도 아니고, 투표도 안했으면 오류발생. 주인이거나 완료된 투표거나, 투표했거나 셋 중 하나만 만족해도 문제없음
		if (!voteEntity.isComplete()
				&& !voteEntity.isOwner(userId)
				&& !existDecisionByVote(userId, parentCommentEntity.getVoteId())) {
			throw new NotFoundDecisionVoteException();
		}

		CommentEntity commentEntity =
				saveComment(
						requestDto.toEntity(
								parentCommentEntity.getVoteId(), userId, parentCommentEntity.getId()));

		String username = getUserNameService.execute(commentEntity);

		return new CreateCommentReplyResponse(commentEntity, true, username, 0);
	}

	private boolean existDecisionByVote(Long userId, Long voteId) {
		List<OptionEntity> options = optionRepository.findAllByVoteId(voteId);

		return options.stream()
				.map(option -> decisionRepository.existsByUserIdAndOptionId(userId, option.getId()))
				.anyMatch(Predicate.isEqual(true));
	}

	private CommentEntity saveComment(CommentEntity commentEntity) {
		return commentRepository.save(commentEntity);
	}
}
