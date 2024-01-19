package com.kakao.golajuma.vote.domain.service;

import com.kakao.golajuma.vote.persistence.entity.HistoryEntity;
import com.kakao.golajuma.vote.persistence.repository.HistoryRepository;
import com.kakao.golajuma.vote.web.dto.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GetHistoryService {
    private final HistoryRepository historyRepository;

    public HistoryResponse execute(Long userId){
        List<HistoryEntity> history = historyRepository.findAllByUserId(userId);

        return HistoryResponse.convert(history);
    }
}
