package com.kakao.golajuma.vote.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kakao.golajuma.vote.domain.exception.vote.NotWriterException;
import com.kakao.golajuma.vote.persistence.entity.VoteEntity;
import com.kakao.golajuma.vote.persistence.repository.VoteRepository;
import com.kakao.golajuma.vote.web.dto.request.AddOptionRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddOptionServiceTest {
	@InjectMocks private AddOptionService addOptionService;
	@Mock VoteRepository voteRepository;

	@DisplayName("투표 옵션 추가")
	@Test
	public void addOptionTest() {
		// given
		Long voteId = 1L;
		Long writerId = 1L;
		Long requestUserId = 1L;
		LocalDateTime date = LocalDateTime.now().plusMinutes(240);
		VoteEntity vote = VoteEntity.builder().id(voteId).userId(writerId).voteEndDate(date).build();

		AddOptionRequest request = new AddOptionRequest();
		AddOptionRequest.OptionDto optionDto = new AddOptionRequest.OptionDto("option1");
		request.getOptions().add(optionDto);

		// when
		when(voteRepository.findById((Long) any())).thenReturn(Optional.of(vote));

		// then
		assertDoesNotThrow(() -> addOptionService.execute(request, voteId, requestUserId));
	}

	@DisplayName("투표 옵션 추가 에러 - 작성자가 아닌 경우")
	@Test
	public void NotWriterError() {
		// given
		Long voteId = 1L;
		Long writerId = 1L;
		Long requestUserId = 2L;
		LocalDateTime date = LocalDateTime.now().plusMinutes(240);
		VoteEntity vote = VoteEntity.builder().id(voteId).userId(writerId).voteEndDate(date).build();

		AddOptionRequest request = new AddOptionRequest();
		AddOptionRequest.OptionDto optionDto = new AddOptionRequest.OptionDto("option1");
		request.getOptions().add(optionDto);

		// when
		when(voteRepository.findById((Long) any())).thenReturn(Optional.of(vote));

		// then
		assertThrows(
				NotWriterException.class, () -> addOptionService.execute(request, voteId, requestUserId));
	}
}
