package com.example.demo.controllers;

import com.example.demo.controllers.erors.ForbiddenException;
import com.example.demo.controllers.erors.MachineIsNotActive;
import com.example.demo.model.Error;
import com.example.demo.model.Machine;
import com.example.demo.model.Permissions;
import com.example.demo.model.Status;
import com.example.demo.requests.MachineRequestCreate;
import com.example.demo.requests.MachineRequestSearch;
import com.example.demo.requests.ScheduleRequest;
import com.example.demo.services.ErrorService;
import com.example.demo.services.MachineService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/machine")
public class MachineController {

    private final MachineService machineService;
    private final UserService userService;
    private final ErrorService errorService;

    @Autowired
    public MachineController(MachineService machineService, UserService userService,ErrorService errorService) {
        this.machineService = machineService;
        this.userService = userService;
        this.errorService = errorService;
    }
    @GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Error> getAllError(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_search_machines)){
            return errorService.findAll(authentication.getName());
        }else {
            throw new ForbiddenException("You don't have permission to see errors");
        }
    }
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Machine> getAllMachine(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_search_machines)){
            return machineService.findAllUser(authentication.getName());
        }else {
            throw new ForbiddenException("You don't have permission to see machines");
        }
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Machine getMachine(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_search_machines)){
            return machineService.findByIdSure(id);
        }else {
            throw new ForbiddenException("You don't have permission to see machines");
        }
    }
    @GetMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void startMachine(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_start_machines)){
            machineService.start(id);
        }else {
            throw new ForbiddenException("You don't have permission to start machines");
        }
    }
    @GetMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void stopMachine(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_stop_machines)){
            machineService.stop(id);
        }else {
            throw new ForbiddenException("You don't have permission to stop machines");
        }
    }
    @GetMapping(value = "/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void restartMachine(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_restart_machines)){
            machineService.restart(id);
        }else {
            throw new ForbiddenException("You don't have permission to restart machines");
        }
    }
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Machine> postSearch(@Valid @RequestBody MachineRequestSearch machineRequestSearch){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_search_machines)){

            return machineService.findAllSearch(authentication.getName(), machineRequestSearch.getStatus(), machineRequestSearch.getName(), machineRequestSearch.getDateFrom(), machineRequestSearch.getDateTo());
        }else {
            throw new ForbiddenException("You don't have permission to see machines");
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMachine(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        System.out.println(tem);
        if(tem.contains(Permissions.can_destroy_machines)){
            machineService.deleteById(id);
        }else {
            throw new ForbiddenException("You don't have permission to delete a machine");

        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Machine createMachine(@Valid @RequestBody MachineRequestCreate name){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_create_machines)){
            Machine machine = new Machine();
            machine.setMachineName(name.getName());
            machine.setCreatedBy(userService.findUserByUsername(authentication.getName()));
            machine.setCreatedAt(LocalDate.now());
            machine.setStatus(Status.STOPPED);
            machine.setActive(Boolean.TRUE);
            return machineService.createMachine(machine);
        }else {
            throw new ForbiddenException("You don't have permission to create a machine");

        }
    }
    @PostMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public void scheduleMachine(@Valid @RequestBody ScheduleRequest s){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(s.getMachineId() + " " + s.getDate() + " " + s.getFun());
        HashSet tem = (HashSet) authentication.getCredentials();
        Machine m = machineService.findByIdSure(s.getMachineId());
        if (!m.getActive()){
            throw new MachineIsNotActive("Machine is not active ");
        }else if (!m.getCreatedBy().getEmail().equals(authentication.getName())){
            throw new ForbiddenException("You don't have permission to schedule this machine");
        }
        if (s.getFun().equals("start")){
            if(tem.contains(Permissions.can_start_machines)){
                machineService.schedule(s.getMachineId(),s.getDate(),s.getFun(), userService.findUserByUsername(authentication.getName()));
            }else {
                throw new ForbiddenException("You don't have permission to start a machine");
            }
        }else if (s.getFun().equals("stop")){
            if(tem.contains(Permissions.can_stop_machines)){
                machineService.schedule(s.getMachineId(),s.getDate(),s.getFun(),userService.findUserByUsername(authentication.getName()));
            }else {
                throw new ForbiddenException("You don't have permission to stop a machine");

            }
        }else if (s.getFun().equals("restart")){
            if(tem.contains(Permissions.can_restart_machines)){
                machineService.schedule(s.getMachineId(),s.getDate(),s.getFun(),userService.findUserByUsername(authentication.getName()));
            }else {
                throw new ForbiddenException("You don't have permission to restart a machine");

            }
        }else {
            throw new MachineIsNotActive("BAD_REQUEST ");
        }
    }
}

