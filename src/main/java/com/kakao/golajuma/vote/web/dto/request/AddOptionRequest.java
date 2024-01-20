package com.kakao.golajuma.vote.web.dto.request;

import com.kakao.golajuma.common.marker.AbstractRequestDto;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class AddOptionRequest implements AbstractRequestDto {
    List<RequestOptionDto> options;

    @Getter
    public static class OptionDto extends RequestOptionDto{
        public OptionDto(String name, String image) {
            super(name, image);
        }
        public OptionDto(String name){
            super(name, null);
        }
    }

}
