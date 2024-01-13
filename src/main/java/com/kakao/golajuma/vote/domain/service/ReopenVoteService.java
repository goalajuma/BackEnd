package com.kakao.golajuma.vote.domain.service;

import com.kakao.golajuma.vote.domain.exception.vote.AlreadyCloseException;
import com.kakao.golajuma.vote.domain.exception.vote.NotFoundVoteException;
import com.kakao.golajuma.vote.domain.exception.vote.NotWriterException;
import com.kakao.golajuma.vote.domain.exception.vote.OnException;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import com.kakao.golajuma.vote.web.dto.request.ReopenVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ReopenVoteService {
    private final VoteRepository voteRepository;

    public void execute(Long voteId, Long userId, ReopenVoteRequest requestDto) {
        VoteEntity voteEntity =
                voteRepository.findById(voteId).orElseThrow(NotFoundVoteException::new);
        if (!voteEntity.isOwner(userId)) {
            throw new NotWriterException();
        }
        if (voteEntity.isOn()) {
            throw new OnException();
        }
        int timeLimit = requestDto.getTimeLimit();
        voteEntity.reopen(timeLimit);
    }
}
