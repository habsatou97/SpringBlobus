package com.blobus.apiexterneblobus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "ChangePinCode  not allowed", value =HttpStatus.BAD_REQUEST)

public class ChangePinCodeException extends Throwable {

    public ChangePinCodeException (String message){
        super();
    }
}
