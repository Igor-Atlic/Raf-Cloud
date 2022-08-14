package com.example.demo.controllers.erors;

public class ScheduleError extends RuntimeException {

    public ScheduleError(String message){
        super(message);
    }

}
