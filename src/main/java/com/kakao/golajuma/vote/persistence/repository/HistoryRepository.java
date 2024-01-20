package com.kakao.golajuma.vote.persistence.repository;

import com.kakao.golajuma.vote.persistence.entity.HistoryEntity;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    @Query(
            "select h from HistoryEntity h"
                    + " where h.deleted = false"
                    + " ORDER BY h.createdDate desc ")
    List<HistoryEntity> findAllByUserId(Long userId, Pageable pageable);
}
