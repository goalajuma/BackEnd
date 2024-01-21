package com.kakao.golajuma.comment.persistence.repository;

import com.kakao.golajuma.comment.persistence.entity.LikeyEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeyRepository extends JpaRepository<LikeyEntity, Long> {
	@Query("select count(l) from LikeyEntity l where l.commentId = :commentId and l.deleted = false")
	int countByCommentId(@Param("commentId") Long commentId);

	@Query("select l from LikeyEntity l where l.commentId = :commentId and l.userId = :userId")
	Optional<LikeyEntity> findByCommentIdUserId(
			@Param("commentId") Long commentId, @Param("userId") Long userId);
}
