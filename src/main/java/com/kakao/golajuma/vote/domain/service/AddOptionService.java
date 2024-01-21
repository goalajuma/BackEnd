package com.kakao.golajuma.vote.domain.service;

import com.kakao.golajuma.vote.domain.exception.vote.NotFoundVoteException;
import com.kakao.golajuma.vote.domain.exception.vote.NotWriterException;
import com.kakao.golajuma.vote.persistence.entity.OptionEntity;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.OptionRepository;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import com.kakao.golajuma.vote.util.ImageUploader;
import com.kakao.golajuma.vote.web.dto.request.AddOptionRequest;
import com.kakao.golajuma.vote.web.dto.request.RequestOptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AddOptionService {

	private final VoteRepository voteRepository;
	private final OptionRepository optionRepository;
	private final ImageUploader imageUploader;

	public void execute(AddOptionRequest request, Long voteId, Long userId) {
		VoteEntity voteEntity = voteRepository.findById(voteId).orElseThrow(NotFoundVoteException::new);

		if (!voteEntity.isOwner(userId)) {
			throw new NotWriterException();
		}

		for (RequestOptionDto optionDto : request.getOptions()) {
			OptionEntity optionEntity = OptionEntity.create(imageUploader, optionDto, voteId);
			optionRepository.save(optionEntity);
		}
	}
}
