package com.blobus.apiexterneblobus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Transaction not allowed", value =HttpStatus.BAD_REQUEST)
public class GetTransactionException extends RuntimeException{

    public GetTransactionException(String message){
        super(message);
    }
}
