package com.dododo.kt.controller.dto;

import com.dododo.kt.converter.GameModeConverter;
import com.dododo.kt.model.BasicInfo;
import com.dododo.kt.model.GameMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.persistence.Convert;

@Data
public class GameParamsRequestDTO {

    @NotEmpty(message = "Invalid token value", groups = BasicInfo.class)
    private String token;

    @Convert(converter = GameModeConverter.class)
    private GameMode gameMode;

    @NotNull(message = "Invalid trackId value", groups = BasicInfo.class)
    private Integer trackId;
}