package com.blobus.apiexterneblobus.exception;
import com.blobus.apiexterneblobus.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmailAlreadyExistException.class, IllegalArgumentException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResponse> handlerResourceEmailNotExist(Exception ex){
        return new ResponseEntity<>(ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).success(true).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
