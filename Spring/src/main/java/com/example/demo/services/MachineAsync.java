package com.example.demo.services;

import com.example.demo.model.Machine;
import com.example.demo.model.Status;
import com.example.demo.repositories.MachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MachineAsync {
    @Autowired
    private MachineRepo machineRepo;

    @Async
    public void asyncStart(Machine machine){
        long time = 10000 + new Random().nextInt(2000);
        System.out.println("start: " + Thread.currentThread().getName() + ", index : ");
        try { Thread.sleep(time);} catch(Exception e) {}
        machine.setStatus(Status.RUNNING);
        machine.setmLock(Boolean.FALSE);
        machineRepo.save(machine);
        System.out.println("end  : " + Thread.currentThread().getName() + ", index : ");
    }

    @Async
    public void asyncStop(Machine machine){
        long time = 10000 + new Random().nextInt(2000);
        System.out.println("start: " + Thread.currentThread().getName() + ", index : ");
        try { Thread.sleep(time);} catch(Exception e) {}
        machine.setStatus(Status.STOPPED);
        machine.setmLock(Boolean.FALSE);
        machineRepo.save(machine);
        System.out.println("end  : " + Thread.currentThread().getName() + ", index : ");
    }

    @Async
    public void asyncRestart(Machine machine){
        long time = 10000 + new Random().nextInt(2000);
        System.out.println("start: " + Thread.currentThread().getName() + ", index : ");
        try { Thread.sleep(time/2);} catch(Exception e) {}
        machine.setStatus(Status.STOPPED);
        machineRepo.save(machine);
        try { Thread.sleep(time/2);} catch(Exception e) {}
        machine.setStatus(Status.RUNNING);
        machine.setmLock(Boolean.FALSE);
        machineRepo.save(machine);
        System.out.println("end  : " + Thread.currentThread().getName() + ", index : ");
    }
}
