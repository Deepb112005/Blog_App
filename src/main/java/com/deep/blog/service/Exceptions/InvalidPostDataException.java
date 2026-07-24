package com.deep.blog.service.Exceptions;

public class InvalidPostDataException extends RuntimeException {
    public InvalidPostDataException(String message) {
        super(message);
    }
}