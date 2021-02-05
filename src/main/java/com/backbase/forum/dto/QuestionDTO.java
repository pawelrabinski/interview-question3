package com.backbase.forum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Builder
public class QuestionDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    @NotEmpty
    private String message;

    private Integer replies;
}
