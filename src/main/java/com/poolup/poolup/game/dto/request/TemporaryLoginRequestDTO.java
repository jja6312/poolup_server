package com.poolup.poolup.game.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemporaryLoginRequestDTO {
    private String email;
}
