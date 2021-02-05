package com.backbase.forum.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String msg) {
        super(msg);
    }
}
