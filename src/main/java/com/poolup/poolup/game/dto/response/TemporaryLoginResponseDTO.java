package com.poolup.poolup.game.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemporaryLoginResponseDTO {
    private Long id;
    private String name;
    private String email;

}
