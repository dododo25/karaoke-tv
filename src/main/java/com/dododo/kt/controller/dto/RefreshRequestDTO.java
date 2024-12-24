package com.dododo.kt.controller.dto;

import com.dododo.kt.model.BasicInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshRequestDTO {

    @NotEmpty(message = "Invalid token value", groups = BasicInfo.class)
    private String token;

    private String type;
}