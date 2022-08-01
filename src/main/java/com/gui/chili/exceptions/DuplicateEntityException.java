package com.gui.chili.exceptions;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(){}
    public DuplicateEntityException(String message){
        super(message);
    }
}
