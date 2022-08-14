package com.example.demo.services;

import com.example.demo.controllers.erors.*;
import com.example.demo.model.Error;
import com.example.demo.model.Machine;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repositories.MachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private MachineRepo machineRepo;
    private MachineAsync machineAsync;
    private TaskScheduler taskScheduler;
    private ErrorService errorService;

    @Autowired
    public MachineService(MachineRepo machineRepo,MachineAsync machineAsync,TaskScheduler taskScheduler,ErrorService errorService){
        this.machineRepo = machineRepo; this.machineAsync = machineAsync;this.taskScheduler = taskScheduler;this.errorService = errorService;}

    public List<Machine> findAll(){return (List<Machine>)machineRepo.findAll();}
    public List<Machine> findAllUser(String user){return (List<Machine>)machineRepo.findAllByCreatedByEmailLikeAndActiveIsTrue(user);}
    public List<Machine> findAllSearch(String user, List<Status> status, String name, LocalDate dateFrom, LocalDate dateTo){return (List<Machine>)machineRepo.findAllByCreatedByEmailLikeAndStatusInAndMachineNameContainsIgnoreCaseAndCreatedAtBetweenAndActiveIsTrue(user,status,name,dateFrom,dateTo);}

    public Optional<Machine> findById(Long machineID) { return machineRepo.findById(machineID); }

    public Machine findByIdSure(Long machineID) {
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        return tem1;
    }

    public void deleteById(Long machineID) {
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        if(tem1.getmLock()){
            throw new MachineIsLockedException("Machine with id=" + machineID + " is Locked");
        }
        tem1.setActive(false);
        machineRepo.save(tem1);
    }
    public void lockById(Long machineID){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        tem1.setmLock(Boolean.TRUE);
        machineRepo.save(tem1);
    }

    public Boolean checkLock(Long machineID) {
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        return tem1.getmLock();
    }
    public void start(Long machineID){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1;

        tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        if(tem1.getmLock()){ throw new MachineIsLockedException("Machine with id=" + machineID + " is Locked");}
        if(!tem1.getActive()){ throw new MachineIsNotActive("Machine with id=" + machineID + " is not Active");}
        if(tem1.getStatus().equals(Status.RUNNING)){ throw new MachineBadRequest("Machine with id=" + machineID + " is already RUNNING");}

        tem1.setmLock(Boolean.TRUE);
        machineRepo.save(tem1);
        System.out.println("start: " + Thread.currentThread().getName());
        machineAsync.asyncStart(tem1);
        System.out.println("end  : " + Thread.currentThread().getName());

    }
    public void startSch(Long machineID, User user){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1;
        try {
            tem1 = tem.orElseThrow(() -> new ScheduleError("Machine with id=" + machineID + " doesn't exist"));
            if(tem1.getmLock()){ throw new ScheduleError("Machine with id=" + machineID + " is Locked");}
            if(!tem1.getActive()){ throw new ScheduleError("Machine with id=" + machineID + " is not Active");}
            if(tem1.getStatus().equals(Status.RUNNING)){ throw new ScheduleError("Machine with id=" + machineID + " is already RUNNING");}
            tem1.setmLock(Boolean.TRUE);
            machineRepo.save(tem1);
            System.out.println("start: " + Thread.currentThread().getName());
            machineAsync.asyncStart(tem1);
            System.out.println("end  : " + Thread.currentThread().getName());
        }catch (ScheduleError e){
            Error er = new Error();
            er.setCreatedBy(user);
            er.setMessage(e.getMessage());
            Date d = new Date();
            er.setDate(d);
            er.setMachineId(machineID);
            er.setFun("Start");
            errorService.createError(er);
        }

    }
    public void stop(Long machineID){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1;
        tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        if(tem1.getmLock()){ throw new MachineIsLockedException("Machine with id=" + machineID + " is Locked");}
        if(!tem1.getActive()){ throw new MachineIsNotActive("Machine with id=" + machineID + " is not Active");}
        if(tem1.getStatus().equals(Status.STOPPED)){ throw new MachineBadRequest("Machine with id=" + machineID + " is already STOPPED");}
        tem1.setmLock(Boolean.TRUE);
        machineRepo.save(tem1);
        System.out.println("start: " + Thread.currentThread().getName());
        machineAsync.asyncStop(tem1);
        System.out.println("end  : " + Thread.currentThread().getName());


    }
    public void stopSch(Long machineID, User user){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1;
        try {
            tem1 = tem.orElseThrow(() -> new ScheduleError("Machine with id=" + machineID + " doesn't exist"));
            if(tem1.getmLock()){ throw new ScheduleError("Machine with id=" + machineID + " is Locked");}
            if(!tem1.getActive()){ throw new ScheduleError("Machine with id=" + machineID + " is not Active");}
            if(tem1.getStatus().equals(Status.STOPPED)){ throw new ScheduleError("Machine with id=" + machineID + " is already STOPPED");}
            tem1.setmLock(Boolean.TRUE);
            machineRepo.save(tem1);
            System.out.println("start: " + Thread.currentThread().getName());
            machineAsync.asyncStop(tem1);
            System.out.println("end  : " + Thread.currentThread().getName());
        }catch (ScheduleError e){
            Error er = new Error();
            er.setCreatedBy(user);
            er.setMessage(e.getMessage());
            Date d = new Date();
            er.setDate(d);
            er.setMachineId(machineID);
            er.setFun("Stop");
            errorService.createError(er);
        }

    }
    public void restart(Long machineID){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1;

        tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        if(tem1.getmLock()){ throw new MachineIsLockedException("Machine with id=" + machineID + " is Locked");}
        if(!tem1.getActive()){ throw new MachineIsNotActive("Machine with id=" + machineID + " is not Active");}
        if(tem1.getStatus().equals(Status.STOPPED)){ throw new MachineBadRequest("Machine with id=" + machineID + " is not RUNNING");}

        tem1.setmLock(Boolean.TRUE);
        machineRepo.save(tem1);
        System.out.println("start: " + Thread.currentThread().getName());
        machineAsync.asyncRestart(tem1);
        System.out.println("end  : " + Thread.currentThread().getName());
    }

    public void restartSch(Long machineID, User user){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1;
        try {
            tem1 = tem.orElseThrow(() -> new ScheduleError("Machine with id=" + machineID + " doesn't exist"));
            if(tem1.getmLock()){ throw new ScheduleError("Machine with id=" + machineID + " is Locked");}
            if(!tem1.getActive()){ throw new ScheduleError("Machine with id=" + machineID + " is not Active");}
            if(tem1.getStatus().equals(Status.STOPPED)){ throw new ScheduleError("Machine with id=" + machineID + " is not RUNNING");}
            tem1.setmLock(Boolean.TRUE);
            machineRepo.save(tem1);
            System.out.println("start: " + Thread.currentThread().getName());
            machineAsync.asyncRestart(tem1);
            System.out.println("end  : " + Thread.currentThread().getName());
        }catch (ScheduleError e){
            Error er = new Error();
            er.setCreatedBy(user);
            er.setMessage(e.getMessage());
            Date d = new Date();
            er.setDate(d);
            er.setMachineId(machineID);
            er.setFun("Restart");
            errorService.createError(er);
        }
    }

    public Machine createMachine(Machine machine){ return this.machineRepo.save(machine); }

    public Machine updateMachine(Machine machine, Long machineID){
        Optional<Machine> tem = machineRepo.findById(machineID);
        Machine tem1 = tem.orElseThrow(() -> new MachineDoesntExistException("Machine with id=" + machineID + " doesn't exist"));
        if(tem1.getmLock()){
            throw new MachineIsLockedException("Machine with id=" + machineID + " is Locked");
        }
        tem1.setMachineName(machine.getMachineName());
        tem1.setStatus(machine.getStatus());
        tem1.setActive(machine.getActive());
        return machineRepo.save(tem1);

    }

    public void schedule(Long Id, List<Integer> date, String fun, User user) {
        Date tem = new Date(date.get(0)-1900,date.get(1)- 1, date.get(2),date.get(3),date.get(4));
        System.out.println(tem);
        this.taskScheduler.schedule(() -> {
            if (fun.equals("start")){
                startSch(Id, user);
            }else if (fun.equals("stop")){
                stopSch(Id,user);
            }else if (fun.equals("restart")){
                restartSch(Id , user);
            }
        }, tem);
    }

}
