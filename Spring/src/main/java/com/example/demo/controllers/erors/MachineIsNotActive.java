package com.example.demo.controllers.erors;

public class MachineIsNotActive extends RuntimeException{

    public MachineIsNotActive(String message){
        super(message);
    }
}
