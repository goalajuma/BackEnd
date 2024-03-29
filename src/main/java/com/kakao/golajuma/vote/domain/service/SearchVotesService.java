package com.kakao.golajuma.vote.domain.service;

import com.kakao.golajuma.vote.persistence.entity.Category;
import com.kakao.golajuma.vote.persistence.entity.HistoryEntity;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.HistoryRepository;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import com.kakao.golajuma.vote.web.dto.response.SearchVotesResponse;
import com.kakao.golajuma.vote.web.dto.response.VoteDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchVotesService {

	private final VoteRepository voteRepository;
	private final GetVoteService getVoteService;
	private final HistoryRepository historyRepository;
	static int page = 0;
	static int size = 5;

	public SearchVotesResponse execute(
			Long userId, int page, String keyword, Sort sort, Category category) {
		this.page = page;

		Slice<VoteEntity> voteEntities = findVotes(keyword, category, sort);

		List<VoteDto> votes =
				voteEntities.stream()
						.map(voteEntity -> getVoteService.execute(voteEntity, userId))
						.collect(Collectors.toList());

		boolean isLast = voteEntities.isLast();

		HistoryEntity historyEntity = HistoryEntity.create(userId, keyword);
		historyRepository.save(historyEntity);

		return SearchVotesResponse.convert(votes, isLast);
	}

	private Slice<VoteEntity> findVotes(String keyword, Category category, Sort sort) {
		if (Category.isTotalRequest(category)) {
			return OrderBySort(keyword, sort);
		}
		return ByCategoryOrderBySort(keyword, category, sort);
	}

	private Slice<VoteEntity> OrderBySort(String keyword, Sort sort) {
		Pageable pageable = PageRequest.of(page, size);

		if (Sort.isCurrentRequest(sort)) {
			return voteRepository.searchVotesOrderByCreatedDate(keyword, pageable);
		}
		return voteRepository.searchVotesOrderByVoteTotalCount(keyword, pageable);
	}

	private Slice<VoteEntity> ByCategoryOrderBySort(String keyword, Category category, Sort sort) {
		Pageable pageable = PageRequest.of(page, size);

		if (Sort.isCurrentRequest(sort)) {
			return voteRepository.searchVotesByCategoryOrderByCreatedDate(keyword, category, pageable);
		}
		return voteRepository.searchVotesByCategoryOrderByVoteTotalCount(keyword, category, pageable);
	}
}
