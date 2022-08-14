package com.example.demo.controllers.erors;

public class MachineBadRequest extends RuntimeException{

    public MachineBadRequest(String message){
        super(message);
    }
}
