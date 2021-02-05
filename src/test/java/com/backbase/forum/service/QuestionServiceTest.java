package com.backbase.forum.service;

import com.backbase.forum.dto.QuestionDTO;
import com.backbase.forum.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class QuestionServiceTest {

    private ObjectConverter objectConverter;

    private QuestionRepository questionRepository;

    private QuestionService questionService;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setup(){
        questionRepository = Mockito.mock(QuestionRepository.class);
        objectConverter = new ObjectConverter();
        questionService = new QuestionService(questionRepository, objectConverter);
    }

    /**
     * Scenario: Given questionRepository returning empty list, getAllQuestions method returns not null object
     */
    @Test
    void shouldGetAllQuestionsMethodNotReturnNull() {
        //given
        when(questionRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<QuestionDTO> questionDTOList = questionService.getAllQuestions();

        //then
        assertNotNull(questionDTOList);
    }
}