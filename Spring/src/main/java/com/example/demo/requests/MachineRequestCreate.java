package com.example.demo.requests;

import lombok.Data;

@Data
public class MachineRequestCreate {
    private String machineName;

    public String getName() {
        return machineName;
    }

    public void setName(String machineName) {
        this.machineName = machineName;
    }
}
