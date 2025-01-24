package com.poolup.poolup.game.controller.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemporaryLoginResponse {
    private Long memberId;
    private String name;
    private String email;

}
