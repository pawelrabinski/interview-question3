package com.backbase.forum.controller;

import com.backbase.forum.dto.QuestionDTO;
import com.backbase.forum.dto.ReplyDTO;
import com.backbase.forum.dto.ThreadDTO;
import com.backbase.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(@Autowired QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questionDTOs = questionService.getAllQuestions();
        return new ResponseEntity<>(questionDTOs, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreadDTO> getThread(@PathVariable("id") Long threadId) {
        return new ResponseEntity<>(
                questionService.getThread(threadId),
                new HttpHeaders(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> addNewQuestion(@Valid @RequestBody QuestionDTO newQuestion) {
        return new ResponseEntity<>(questionService.addNewQuestion(newQuestion),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<ReplyDTO> addReplyToQuestion(@Valid @RequestBody ReplyDTO reply, @PathVariable("id") Long questionId) {
        return new ResponseEntity<>(questionService.addReplyToQuestion(reply, questionId),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }

}
