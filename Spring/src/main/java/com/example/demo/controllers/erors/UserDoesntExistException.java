package com.example.demo.controllers.erors;

public class UserDoesntExistException extends RuntimeException{

    public UserDoesntExistException(String message){
        super(message);
    }
}
