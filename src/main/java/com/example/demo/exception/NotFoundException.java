package com.example.demo.exception;

public class NotFoundException extends AimsException {

    public int getStatusCode() {
        return 404;
    }

    public NotFoundException(String message)
    {
        super(message);
    }
}
