    package com.example.student.exception;
    
    public class StudentNotFoundException extends RuntimeException {
        public StudentNotFoundException(String s) {
            super(s);
        }
    }
