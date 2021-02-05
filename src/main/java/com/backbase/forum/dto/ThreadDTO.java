package com.backbase.forum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class ThreadDTO {

    private Long id;

    private String author;

    private String message;

    private List<ReplyDTO> replies;
}
