package com.kakao.golajuma.auth.web.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAuthCodeRequest extends ValidEmailRequest {
	@NotBlank(message = ValidExceptionMessage.EMPTY_MESSAGE)
	String authCode;
}
