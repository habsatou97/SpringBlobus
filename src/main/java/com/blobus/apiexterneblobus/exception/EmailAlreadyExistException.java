package com.blobus.apiexterneblobus.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String s) {
        super(s);
    }

    public EmailAlreadyExistException() {
        super("Email Already taken.");
    }
}
