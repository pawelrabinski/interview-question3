package com.backbase.forum.service;

import com.backbase.forum.dto.QuestionDTO;
import com.backbase.forum.dto.ReplyDTO;
import com.backbase.forum.dto.ThreadDTO;
import com.backbase.forum.entity.QuestionEntity;
import com.backbase.forum.entity.ReplyEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ObjectConverter {

    public QuestionEntity convertQuestionDTOtoEntity(QuestionDTO questionDTO) {
        return QuestionEntity.builder()
                .author(questionDTO.getAuthor())
                .message(questionDTO.getMessage())
                .build();
    }

    public QuestionDTO convertQuestionEntityToDTO(QuestionEntity questionEntity) {
        return QuestionDTO.builder()
                .id(questionEntity.getId())
                .author(questionEntity.getAuthor())
                .message(questionEntity.getMessage())
                .replies(questionEntity.getReplies() != null ? questionEntity.getReplies().size() : 0)
                .build();
    }

    public ReplyEntity convertReplyDTOtoEntity(ReplyDTO replyDTO) {
        return ReplyEntity.builder()
                .author(replyDTO.getAuthor())
                .message(replyDTO.getMessage())
                .build();
    }

    public ReplyDTO convertReplyEntityToDTO(ReplyEntity replyEntity, Long questionId) {
        return ReplyDTO.builder()
                .id(replyEntity.getId())
                .questionId(questionId)
                .author(replyEntity.getAuthor())
                .message(replyEntity.getMessage())
                .build();
    }

    public ThreadDTO convertQuestionEntityToThreadDTO(QuestionEntity questionEntity) {
        return ThreadDTO.builder()
                .id(questionEntity.getId())
                .author(questionEntity.getAuthor())
                .message(questionEntity.getMessage())
                .replies(questionEntity.getReplies()
                        .stream()
                        .map(r -> convertReplyEntityToDTO(r, r.getQuestion().getId()))
                        .collect(Collectors.toList()))
                .build();
    }
}
