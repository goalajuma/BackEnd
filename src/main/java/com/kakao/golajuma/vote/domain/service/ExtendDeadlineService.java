package com.kakao.golajuma.vote.domain.service;

import com.kakao.golajuma.vote.domain.exception.vote.NotFoundVoteException;
import com.kakao.golajuma.vote.domain.exception.vote.NotWriterException;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import com.kakao.golajuma.vote.web.dto.request.ExtendDeadlineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ExtendDeadlineService {
	private final VoteRepository voteRepository;

	public void execute(Long voteId, Long userId, ExtendDeadlineRequest requestDto) {
		VoteEntity voteEntity = voteRepository.findById(voteId).orElseThrow(NotFoundVoteException::new);
		if (!voteEntity.isOwner(userId)) {
			throw new NotWriterException();
		}
		int timeLimit = requestDto.getTimeLimit();
		voteEntity.extendDeadline(timeLimit);
	}
}
