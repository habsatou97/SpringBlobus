package com.blobus.apiexterneblobus.exception;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(){
        super("Entity with this id doesn't exists");
    }
}
