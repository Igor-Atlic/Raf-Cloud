package com.example.demo.controllers.erors;

public class MachineDoesntExistException extends RuntimeException{

    public MachineDoesntExistException(String message){
        super(message);
    }

}
