package com.kakao.golajuma.vote.web.dto.request;

import com.kakao.golajuma.common.marker.AbstractRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReopenVoteRequest implements AbstractRequestDto {
    int timeLimit;

    public ReopenVoteRequest(int timeLimit){
        this.timeLimit = timeLimit;
    }
}
