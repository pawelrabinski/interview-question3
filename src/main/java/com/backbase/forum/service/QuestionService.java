package com.backbase.forum.service;

import com.backbase.forum.dto.QuestionDTO;
import com.backbase.forum.dto.ReplyDTO;
import com.backbase.forum.dto.ThreadDTO;
import com.backbase.forum.entity.QuestionEntity;
import com.backbase.forum.entity.ReplyEntity;
import com.backbase.forum.exception.QuestionNotFoundException;
import com.backbase.forum.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private static final String NOT_FOUND_MSG = "Question with id {id} not found";

    private QuestionRepository questionRepository;

    private ObjectConverter objectConverter;

    public QuestionService(@Autowired QuestionRepository questionRepository, @Autowired ObjectConverter objectConverter) {
        this.questionRepository = questionRepository;
        this.objectConverter = objectConverter;
    }

    /**
     * Given QuestionDTO object, stores QuestionEntity and returns mapped QuestionDTO object
     *
     * @param questionDTO - QuestionDTO object to be saved
     * @return QuestionDTO
     */
    public QuestionDTO addNewQuestion(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = objectConverter.convertQuestionDTOtoEntity(questionDTO);
        questionEntity = questionRepository.save(questionEntity);
        return objectConverter.convertQuestionEntityToDTO(questionEntity);
    }


    /**
     * Retrieves list of stored QuestionEntity objects and returns mapped list of QuestionDTO objects
     *
     * @return List of QuestionDTO
     */
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(q -> objectConverter.convertQuestionEntityToDTO(q))
                .collect(Collectors.toList());
    }

    /**
     * Given ReplyDTO object and Long object with questionId, stores ReplyEntity and returns mapped ReplyDTO object
     *
     * @param replyDTO - ReplyDTO object to be saved
     * @param questionId - id of the question related to the reply
     * @return ReplyDTO
     */
    public ReplyDTO addReplyToQuestion(ReplyDTO replyDTO, Long questionId) {
        ReplyEntity replyEntity = objectConverter.convertReplyDTOtoEntity(replyDTO);
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(NOT_FOUND_MSG.replace("{id}", questionId.toString())));

        replyEntity.setQuestion(questionEntity);
        questionEntity.getReplies().add(replyEntity);
        QuestionEntity savedQuestion = questionRepository.save(questionEntity);

        return objectConverter.convertReplyEntityToDTO(
                savedQuestion.getReplies().get(savedQuestion.getReplies().size() - 1),
                questionId);
    }

    /**
     * Given Long object with question id, retrieves QuestionEntity and returns mapped ThreadDTO object
     *
     * @param questionId - id of the question mapped to requested thread
     * @return QuestionDTO
     */
    public ThreadDTO getThread(Long questionId) {
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(NOT_FOUND_MSG.replace("{id}", questionId.toString())));
        return objectConverter.convertQuestionEntityToThreadDTO(questionEntity);
    }
}
