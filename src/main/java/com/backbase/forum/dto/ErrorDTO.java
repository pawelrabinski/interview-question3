package com.backbase.forum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ErrorDTO {

    private String errorCode;

    private String errorMessage;
}
