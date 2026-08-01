package com.example.student.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String emailAlreadyExists) {
        super(emailAlreadyExists);
    }
}
