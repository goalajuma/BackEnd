package com.kakao.golajuma.auth.web.dto.request;

import com.kakao.golajuma.auth.web.supplier.PasswordSupplier;
import com.kakao.golajuma.common.marker.AbstractRequestDto;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class UpdateUserPasswordRequest implements AbstractRequestDto, PasswordSupplier {
	@NotBlank(message = ValidExceptionMessage.EMPTY_MESSAGE)
	private String newPassword;
}
