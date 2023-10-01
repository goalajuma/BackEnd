package com.kakao.golajuma.auth.domain.service;

import com.kakao.golajuma.auth.domain.exception.DuplicateException;
import com.kakao.golajuma.auth.infra.repository.UserRepository;
import com.kakao.golajuma.auth.web.supplier.EmailSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValidEmailService {
	private final UserRepository userRepository;

	public void execute(EmailSupplier supplier) {
		boolean exists = userRepository.existsByEmail(supplier.getEmail());
		if (exists) {
			throw new DuplicateException("중복되는 이메일입니다");
		}
	}
}