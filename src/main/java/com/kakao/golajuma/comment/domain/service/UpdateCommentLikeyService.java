package com.kakao.golajuma.comment.domain.service;

import com.kakao.golajuma.comment.persistence.entity.LikeyEntity;
import com.kakao.golajuma.comment.persistence.repository.LikeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UpdateCommentLikeyService {

    private final LikeyRepository likeyRepository;

    public void execute(Long commentId, Long userId){
        likeyRepository.findByCommentIdUserId(commentId, userId)
                .ifPresentOrElse(likeyRepository::delete, () -> save(commentId, userId));
    }
    private void save(Long commentId, Long userId){
        LikeyEntity likeyEntity = LikeyEntity.builder()
                .commentId(commentId)
                .userId(userId)
                .build();
        likeyRepository.save(likeyEntity);
    }
}
