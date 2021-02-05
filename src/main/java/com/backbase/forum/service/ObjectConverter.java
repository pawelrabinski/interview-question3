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

    /**
     * Given a QuestionDTO object, parses it to a QuestionEntity object
     *
     * @param questionDTO - QuestionDTO object to be parsed
     * @return QuestionEntity
     */
    public QuestionEntity convertQuestionDTOtoEntity(QuestionDTO questionDTO) {
        return QuestionEntity.builder()
                .author(questionDTO.getAuthor())
                .message(questionDTO.getMessage())
                .build();
    }

    /**
     * Given a QuestionEntity object, parses it to a QuestionDTO object
     *
     * @param questionEntity - QuestionEntity object to be parsed
     * @return QuestionDTO
     */
    public QuestionDTO convertQuestionEntityToDTO(QuestionEntity questionEntity) {
        return QuestionDTO.builder()
                .id(questionEntity.getId())
                .author(questionEntity.getAuthor())
                .message(questionEntity.getMessage())
                .replies(questionEntity.getReplies() != null ? questionEntity.getReplies().size() : 0)
                .build();
    }

    /**
     * Given a ReplyDTO object, parses it to a ReplyEntity object
     *
     * @param replyDTO - QuestionEntity object to be parsed
     * @return ReplyEntity
     */
    public ReplyEntity convertReplyDTOtoEntity(ReplyDTO replyDTO) {
        return ReplyEntity.builder()
                .author(replyDTO.getAuthor())
                .message(replyDTO.getMessage())
                .build();
    }

    /**
     * Given a ReplyEntity object and Long object with the value of questionId, parses it to a ReplyDTO object
     *
     * @param questionId  - Id of a question that reply is related to
     * @param replyEntity - QuestionEntity object to be parsed
     * @return ReplyDTO
     */
    public ReplyDTO convertReplyEntityToDTO(ReplyEntity replyEntity, Long questionId) {
        return ReplyDTO.builder()
                .id(replyEntity.getId())
                .questionId(questionId)
                .author(replyEntity.getAuthor())
                .message(replyEntity.getMessage())
                .build();
    }

    /**
     * Given a QuestionEntity object, parses it to a ThreadDTO object
     *
     * @param questionEntity - QuestionEntity object to be parsed
     * @return ThreadDTO
     */
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
