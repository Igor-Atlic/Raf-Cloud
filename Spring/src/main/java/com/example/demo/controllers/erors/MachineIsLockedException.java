package com.example.demo.controllers.erors;

public class MachineIsLockedException extends RuntimeException{

    public MachineIsLockedException(String message){
        super(message);
    }

}
