package com.example.demo.requests;

import java.util.Date;
import java.util.List;

public class ScheduleRequest {

    private Long machineId;
    private List<Integer> date;
    private String fun;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public List<Integer> getDate() {
        return date;
    }

    public void setDate(List<Integer> date) {
        this.date = date;
    }

    public String getFun() {
        return fun;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }
}
