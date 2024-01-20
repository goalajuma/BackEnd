package com.kakao.golajuma.auth.domain.service;

import com.kakao.golajuma.auth.domain.exception.NotFoundUserException;
import com.kakao.golajuma.auth.domain.helper.Encoder;
import com.kakao.golajuma.auth.persistence.entity.UserEntity;
import com.kakao.golajuma.auth.persistence.repository.UserRepository;
import com.kakao.golajuma.auth.web.dto.request.UpdateUserPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateUserPasswordService {

	private final UserRepository userRepository;

	private final Encoder encoder;
	public void execute(UpdateUserPasswordRequest requestDto, Long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
		String newPassword = requestDto.getNewPassword();
		//비밀번호 유효성?
		userEntity.updatePassword(encoder.encode(newPassword));
	}
}
