package com.backbase.forum.service;

import com.backbase.forum.dto.QuestionDTO;
import com.backbase.forum.dto.ReplyDTO;
import com.backbase.forum.dto.ThreadDTO;
import com.backbase.forum.entity.QuestionEntity;
import com.backbase.forum.entity.ReplyEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectConverterTest {

    private ObjectConverter objectConverter = new ObjectConverter();
    private String author1 = "developer";
    private String author2 = "architect";
    private String message1 = "hi";
    private String message2 = "hello";
    private Long id1 = 1L;
    private Long id2 = 99L;
    private List<ReplyEntity> replyList = new ArrayList<>();


    /**
     * Scenario: Given QuestionDTO object,
     * convertQuestionDTOtoEntity method returns not null, correctly mapped QuestionEntity object
     */
    @Test
    void shouldQuestionDTOconvertToEntity() {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder()
                .id(id1)
                .author(author1)
                .message(message1)
                .replies(99)
                .build();

        //when
        QuestionEntity questionEntity = objectConverter.convertQuestionDTOtoEntity(questionDTO);

        //then
        assertNotNull(questionEntity);
        assertEquals(questionEntity.getAuthor(), questionDTO.getAuthor());
        assertEquals(questionEntity.getMessage(), questionDTO.getMessage());
        assertNull(questionEntity.getId());
        assertNull(questionEntity.getReplies());
    }

    /**
     * Scenario: Given QuestionEntity object,
     * convertQuestionEntityToDTO method returns not null, correctly mapped QuestionDTO object
     */
    @Test
    void shouldQuestionEntityConvertToDTO() {
        //given
        QuestionEntity questionEntity = QuestionEntity.builder()
                .id(id2)
                .author(author2)
                .message(message2)
                .replies(replyList)
                .build();

        //when
        QuestionDTO questionDTO = objectConverter.convertQuestionEntityToDTO(questionEntity);

        //then
        assertNotNull(questionDTO);
        assertEquals(questionDTO.getId(), questionEntity.getId());
        assertEquals(questionDTO.getAuthor(), questionEntity.getAuthor());
        assertEquals(questionDTO.getMessage(), questionEntity.getMessage());
        assertEquals(questionDTO.getReplies(), 0);
    }

    /**
     * Scenario: Given ReplyDTO object,
     * convertReplyDTOtoEntity method returns not null, correctly mapped ReplyEntity object
     */
    @Test
    void shouldReplyDTOconvertToEntity() {
        //given
        ReplyDTO replyDTO = ReplyDTO.builder()
                .id(id2)
                .author(author1)
                .message(message2)
                .questionId(id1)
                .build();

        //when
        ReplyEntity replyEntity = objectConverter.convertReplyDTOtoEntity(replyDTO);

        //then
        assertNotNull(replyEntity);
        assertNull(replyEntity.getId());
        assertNull(replyEntity.getQuestion());
        assertEquals(replyEntity.getAuthor(), replyDTO.getAuthor());
        assertEquals(replyEntity.getMessage(), replyDTO.getMessage());
    }

    /**
     * Scenario: Given QuestionEntity object ReplyEntity object related to QuestionEntity object,
     * convertReplyEntityToDTO method returns not null, correctly mapped ReplyDTO object
     */
    @Test
    void shouldConvertReplyEntityToDTO() {
        //given
        QuestionEntity questionEntity = QuestionEntity.builder()
                .id(id2)
                .author(author2)
                .message(message2)
                .replies(replyList)
                .build();

        ReplyEntity replyEntity = ReplyEntity.builder()
                .id(id1)
                .author(author2)
                .message(message2)
                .question(questionEntity)
                .build();

        //when
        ReplyDTO replyDTO = objectConverter.convertReplyEntityToDTO(replyEntity,questionEntity.getId());

        //then
        assertNotNull(replyDTO);
        assertEquals(replyDTO.getAuthor(), replyEntity.getAuthor());
        assertEquals(replyDTO.getMessage(), replyEntity.getMessage());
        assertEquals(replyDTO.getId(), replyDTO.getId());
        assertEquals(replyDTO.getQuestionId(), replyEntity.getQuestion().getId());
    }

    /**
     * Scenario: Given QuestionEntity object,
     * convertQuestionEntityToThreadDTO method returns not null, correctly mapped ThreadDTO object
     */
    @Test
    void convertQuestionEntityToThreadDTO() {
        //given
        QuestionEntity questionEntity = QuestionEntity.builder()
                .id(id1)
                .author(author2)
                .message(message1)
                .replies(replyList)
                .build();

        //when
        ThreadDTO threadDTO = objectConverter.convertQuestionEntityToThreadDTO(questionEntity);

        //then
        assertNotNull(threadDTO);
        assertEquals(threadDTO.getAuthor(), questionEntity.getAuthor());
        assertEquals(threadDTO.getMessage(), questionEntity.getMessage());
        assertEquals(threadDTO.getId(), questionEntity.getId());
        assertNotNull(threadDTO.getReplies());
        assertEquals(threadDTO.getReplies().size(), questionEntity.getReplies().size());
    }
}