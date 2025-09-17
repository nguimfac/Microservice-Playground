package com.playground.exceptions;


public class NoSuchElementFoundException extends RuntimeException{
    public NoSuchElementFoundException(String message){
        super(message);
    }
}
