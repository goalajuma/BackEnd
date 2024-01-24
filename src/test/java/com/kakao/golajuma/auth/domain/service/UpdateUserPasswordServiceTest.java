package com.kakao.golajuma.auth.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.kakao.golajuma.auth.domain.helper.Encoder;
import com.kakao.golajuma.auth.persistence.entity.UserEntity;
import com.kakao.golajuma.auth.persistence.repository.UserRepository;
import com.kakao.golajuma.auth.web.dto.request.UpdateUserPasswordRequest;
import java.util.Optional;

import com.kakao.golajuma.comment.persistence.entity.CommentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserPasswordServiceTest {
	@InjectMocks private UpdateUserPasswordService updateUserPasswordService;
	@Mock private UserRepository userRepository;

	@Mock private Encoder encoder;

	@Nested
	@DisplayName("유저는 비밀번호를 변경하는데 성공한다.")
	class update_user_password_success_case {

		@Test
		@DisplayName("유저는 비밀번호를 변경하는데 성공한다.")
		void update_user_password_success_test() {
			// given
			Long userId = 1L;

			UpdateUserPasswordRequest requestDto =
					UpdateUserPasswordRequest.builder().newPassword("newPassword").build();

			UserEntity userEntity = mock(UserEntity.class);

			when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

			// when
			updateUserPasswordService.execute(requestDto, userId);

			// then
			verify(userEntity).updatePassword(encoder.encode(requestDto.getNewPassword()));
			assertThat(userEntity.getPassword()).isEqualTo(encoder.encode(requestDto.getNewPassword()));
		}
	}
}
