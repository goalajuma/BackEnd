package com.kakao.golajuma.vote.persistence.entity;

import com.kakao.golajuma.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@ToString
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = HistoryEntity.ENTITY_PREFIX)
public class HistoryEntity extends BaseEntity {

    public static final String ENTITY_PREFIX = "history";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = ENTITY_PREFIX + "_keyword", length = 256, nullable = false)
    private String keyword;

    public static HistoryEntity create(Long userId, String keyword){
        return HistoryEntity.builder()
                .userId(userId)
                .keyword(keyword)
                .build();
    }

}
