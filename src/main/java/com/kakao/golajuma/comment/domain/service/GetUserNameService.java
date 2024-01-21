package com.kakao.golajuma.comment.domain.service;

import com.kakao.golajuma.auth.domain.exception.NotFoundUserException;
import com.kakao.golajuma.auth.persistence.entity.UserEntity;
import com.kakao.golajuma.auth.persistence.repository.UserRepository;
import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import com.kakao.golajuma.comment.persistence.repository.CommentRepository;
import com.kakao.golajuma.vote.domain.exception.vote.NotFoundVoteException;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GetUserNameService {

	private final UserRepository userRepository;

	private final CommentRepository commentRepository;

	private final VoteRepository voteRepository;

	private int count = 1;
	private final HashMap<Long, String[]> usernameMap = new HashMap<>();

	public Map<Long, String[]> execute(List<CommentEntity> commentEntities) {
		putUsernameMapFromEntities(commentEntities);
		return usernameMap;
	}

	public String execute(CommentEntity commentEntity) {
		List<CommentEntity> commentEntities = commentRepository.findByVoteId(commentEntity.getVoteId());
		putUsernameMapFromEntities(commentEntities);
		if (commentEntity.isAnonymous()) return usernameMap.get(commentEntity.getUserId())[1];
		else return usernameMap.get(commentEntity.getUserId())[0];
	}

	private String getUserNickname(Long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
		return userEntity.getNickname();
	}

	private void putUsernameMapFromEntities(List<CommentEntity> commentEntities) {
		VoteEntity voteEntity =
				voteRepository
						.findById(commentEntities.get(0).getVoteId())
						.orElseThrow(NotFoundVoteException::new);
		for (CommentEntity commentEntity : commentEntities) {
			String userNickname = getUserNickname(commentEntity.getUserId());
			usernameMap.computeIfAbsent(
					commentEntity.getUserId(), k -> new String[] {userNickname, null});
			putUsernameMap(voteEntity, commentEntity);
		}
	}

	private void putUsernameMap(VoteEntity voteEntity, CommentEntity commentEntity) {
		String userNickname = getUserNickname(commentEntity.getUserId());
		if (commentEntity.isAnonymous() && voteEntity.isOwner(commentEntity.getUserId())) {
			usernameMap.put(commentEntity.getUserId(), new String[] {userNickname, "익명(글쓴이)"});
			return;
		}
		if (commentEntity.isAnonymous() && usernameMap.get(commentEntity.getUserId())[1] == null) {
			usernameMap.put(commentEntity.getUserId(), new String[] {userNickname, "익명" + count});
			count++;
		}
	}
}
