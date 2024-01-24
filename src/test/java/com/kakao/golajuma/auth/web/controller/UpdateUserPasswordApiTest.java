package com.kakao.golajuma.auth.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.golajuma.auth.domain.token.TokenProvider;
import com.kakao.golajuma.auth.persistence.repository.UserRepository;
import javax.transaction.Transactional;

import com.kakao.golajuma.auth.web.dto.request.UpdateUserPasswordRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UpdateUserPasswordApiTest {
	@Autowired private ObjectMapper om;

	@Autowired private MockMvc mvc;

	@Autowired private TokenProvider tokenProvider;

	@Autowired UserRepository userRepository;

	private String jwtToken;

	@BeforeEach
	void setup() {
		jwtToken = tokenProvider.createAccessToken(10L);
	}

	@DisplayName("유저는 비밀번호를 변경하는데 성공한다.")
	@Transactional
	@Test
	void success_update_Password_test() throws Exception {
		// given
		UpdateUserPasswordRequest requestDto =
				UpdateUserPasswordRequest.builder().newPassword("newPassword123").build();
		String requestBody = om.writeValueAsString(requestDto);
		// when
		ResultActions resultActions =
				mvc.perform(
						MockMvcRequestBuilders.patch("/users/password")
								.header("Authorization", "Bearer " + jwtToken)
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON));
		// then
		resultActions
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
