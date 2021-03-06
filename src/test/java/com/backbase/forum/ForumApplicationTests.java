package com.backbase.forum;

import com.backbase.forum.controller.QuestionController;
import com.backbase.forum.dto.QuestionDTO;
import com.backbase.forum.dto.ReplyDTO;
import com.backbase.forum.dto.ThreadDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ForumApplicationTests {

    private static final String CONTEXT_PATH = "/questions";
    private static final String JSON_CONTENT_TYPE = "application/json";

    @Autowired
    public QuestionController questionController;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Scenario: Spring context is up and questionController initiated and not null
     */
    @Test
    void shouldContextLoads() {
        //given

        //when

        //then
        assertThat(questionController).isNotNull();
    }

    /**
     * Scenario: without any precondition getAllQuestions returns 200 OK status code
     */
    @Test
    void shouldGetAllQuestionsReturn200() {
        //given

        //when
        ResponseEntity<List<QuestionDTO>> responseEntity = questionController.getAllQuestions();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Scenario: Given QuestionDTO with author and message object,
     * method addNewQuestion stores object and return 201 CREATED status code
     */
    @Test
    void shouldAddNewQuestionReturn201() {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();

        //when
        ResponseEntity<QuestionDTO> responseEntity = questionController.addNewQuestion(questionDTO);

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /**
     * Scenario: Given QuestionDTO object containing author and message stored with id = questionId and ReplyDTO object,
     * method addReplyToQuestion stores object and return 201 CREATED status code
     */
    @Test
    void shouldAddNewReplyReturn201() {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();
        ResponseEntity<QuestionDTO> questionDTOResponseEntity = questionController.addNewQuestion(questionDTO);
        ReplyDTO replyDTO = ReplyDTO.builder().author("testAuthor").message("testMessage").build();
        Long questionId = questionDTOResponseEntity.getBody().getId();

        //when
        ResponseEntity<ReplyDTO> responseEntity = questionController.addReplyToQuestion(replyDTO, questionId);

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /**
     * Scenario: Given QuestionDTO object containing author and message stored with id = questionId,
     * method getThread returns 200 OK status code
     */
    @Test
    void shouldGetThreadReturn200() {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();
        ResponseEntity<QuestionDTO> questionDTOResponseEntity = questionController.addNewQuestion(questionDTO);
        Long questionId = questionDTOResponseEntity.getBody().getId();
        //when
        ResponseEntity<ThreadDTO> responseEntity = questionController.getThread(questionId);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Scenario: Given non existing questionId = 9999 and getThread method context path as URL,
     * GET request to URL returns 404 NOT FOUND status code
     */
    @Test
    public void shouldReturn404forNonExistingQuestion() throws Exception {
        //given
        long questionId = 9999L;
        String url = CONTEXT_PATH + "/" + questionId;

        //when
        mockMvc.perform(get(url))

                // then
                .andExpect(status().isNotFound());
    }

    /**
     * Scenario: Given JSON body with author and message fields,
     * POST request to API endpoint returns 201 CREATED status code
     */
    @Test
    public void shouldQuestionWithAuthorAndMessageReturn201() throws Exception {
        //given
        String body = "{\"author\":\"testAuthor\",\"message\":\"testMessage\"}";

        //when
        this.mockMvc.perform(post(CONTEXT_PATH).content(body).contentType(JSON_CONTENT_TYPE))

                //then
                .andExpect(status().isCreated());
    }

    /**
     * Scenario: Given JSON body with message field only,
     * POST request to API endpoint returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldQuestionWithMissingAuthorReturn400() throws Exception {
        //given
        String body = "{\"message\":\"testMessage\"}";

        //when
        this.mockMvc.perform(post(CONTEXT_PATH).content(body).contentType(JSON_CONTENT_TYPE))

                //then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with author field only,
     * POST request to API endpoint returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldQuestionWithMissingMessageReturn400() throws Exception {
        //given
        String body = "{\"author\":\"testAuthor\"}";

        //when
        this.mockMvc.perform(post(CONTEXT_PATH).content(body).contentType(JSON_CONTENT_TYPE))

                //then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with author field and message field with empty value,
     * POST request to API endpoint returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldQuestionWithBlankMessageReturn400() throws Exception {
        //given
        String body = "{\"author\":\"testAuthor\",\"message\":\"\"}";

        //when
        this.mockMvc.perform(post(CONTEXT_PATH).content(body).contentType(JSON_CONTENT_TYPE))

                //then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with message field and author field with empty value,
     * POST request to API endpoint returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldQuestionWithBlankAuthorReturn400() throws Exception {
        //given
        String body = "{\"author\":\"\",\"message\":\"testMessage\"}";

        //when
        this.mockMvc.perform(post(CONTEXT_PATH).content(body).contentType(JSON_CONTENT_TYPE))

                //then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with author and message fields and non existing questionId,
     * POST request to addReplyToQuestion URL returns 404 NOT FOUND status code
     */
    @Test
    public void shouldReturn404forNonExistingQuestionWhileAddingReply() throws Exception {
        //given
        String body = "{\"author\":\"testAuthor\",\"message\":\"testMessage\"}";
        long questionId = 9999L;
        String url = CONTEXT_PATH + "/" + questionId + "/reply";

        //when
        mockMvc.perform(post(url).content(body).contentType(JSON_CONTENT_TYPE))

                // then
                .andExpect(status().isNotFound());
    }

    /**
     * Scenario: Given JSON body with missing message field and existing questionId,
     * POST request to addReplyToQuestion URL returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldReturn400forExistingQuestionWhileAddingReplyWithMissingMessage() throws Exception {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();
        ResponseEntity<QuestionDTO> questionDTOResponseEntity = questionController.addNewQuestion(questionDTO);
        Long questionId = questionDTOResponseEntity.getBody().getId();
        String body = "{\"author\":\"testAuthor\"}";
        String url = CONTEXT_PATH + "/" + questionId + "/reply";

        //when
        mockMvc.perform(post(url).content(body).contentType(JSON_CONTENT_TYPE))

                // then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with empty-valued and existing questionId,
     * POST request to addReplyToQuestion URL returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldReturn400forExistingQuestionWhileAddingReplyWithEmptyMessage() throws Exception {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();
        ResponseEntity<QuestionDTO> questionDTOResponseEntity = questionController.addNewQuestion(questionDTO);
        Long questionId = questionDTOResponseEntity.getBody().getId();
        String body = "{\"author\":\"testAuthor\",\"message\":\"\"}";
        String url = CONTEXT_PATH + "/" + questionId + "/reply";

        //when
        mockMvc.perform(post(url).content(body).contentType(JSON_CONTENT_TYPE))

                // then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with missing author field and existing questionId,
     * POST request to addReplyToQuestion URL returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldReturn400forExistingQuestionWhileAddingReplyWithMissingAuthor() throws Exception {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();
        ResponseEntity<QuestionDTO> questionDTOResponseEntity = questionController.addNewQuestion(questionDTO);
        Long questionId = questionDTOResponseEntity.getBody().getId();
        String body = "{\"message\":\"testMessage\"}";
        String url = CONTEXT_PATH + "/" + questionId + "/reply";

        //when
        mockMvc.perform(post(url).content(body).contentType(JSON_CONTENT_TYPE))

                // then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with empty-valued author field only and existing questionId,
     * POST request to addReplyToQuestion URL returns 400 BAD REQUEST status code
     */
    @Test
    public void shouldReturn400forExistingQuestionWhileAddingReplyWithEmptyAuthor() throws Exception {
        //given
        QuestionDTO questionDTO = QuestionDTO.builder().author("testAuthor").message("testMessage").build();
        ResponseEntity<QuestionDTO> questionDTOResponseEntity = questionController.addNewQuestion(questionDTO);
        Long questionId = questionDTOResponseEntity.getBody().getId();
        String body = "{\"author\":\"\",\"message\":\"testMessage\"}";
        String url = CONTEXT_PATH + "/" + questionId + "/reply";

        //when
        mockMvc.perform(post(url).content(body).contentType(JSON_CONTENT_TYPE))

                // then
                .andExpect(status().isBadRequest());
    }

    /**
     * Scenario: Given JSON body with author and message fields and non existing questionId,
     * POST request to addReplyToQuestion URL returns 404 NOT FOUND status code
     */
    @Test
    public void shouldReturn404AddingReplyWithWrongUrl() throws Exception {
        //given
        String body = "{\"author\":\"testAuthor\",\"message\":\"testMessage\"}";
        long questionId = 9999L;
        String url = CONTEXT_PATH + "/" + questionId + "/incorrect-reply-url";

        //when
        mockMvc.perform(post(url).content(body).contentType(JSON_CONTENT_TYPE))

                // then
                .andExpect(status().isNotFound());
    }

}