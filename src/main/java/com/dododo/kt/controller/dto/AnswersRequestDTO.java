package com.dododo.kt.controller.dto;

import com.dododo.kt.model.BasicInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AnswersRequestDTO {

    @NotEmpty(message = "Invalid token value", groups = BasicInfo.class)
    private String token;

    @NotEmpty(groups = BasicInfo.class)
    private List<String> values;
}