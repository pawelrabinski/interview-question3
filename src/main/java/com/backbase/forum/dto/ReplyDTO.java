package com.backbase.forum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Builder
public class ReplyDTO {

    private Long id;

    private Long questionId;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    @NotEmpty
    private String message;
}
