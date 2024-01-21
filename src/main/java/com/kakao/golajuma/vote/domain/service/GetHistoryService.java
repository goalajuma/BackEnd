package com.kakao.golajuma.vote.domain.service;

import com.kakao.golajuma.vote.persistence.entity.HistoryEntity;
import com.kakao.golajuma.vote.persistence.repository.HistoryRepository;
import com.kakao.golajuma.vote.web.dto.response.HistoryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GetHistoryService {
	private final HistoryRepository historyRepository;

	public HistoryResponse execute(Long userId) {
		Pageable pageable = PageRequest.of(0, 5);
		List<HistoryEntity> history = historyRepository.findAllByUserId(userId, pageable);

		return HistoryResponse.convert(history);
	}
}
