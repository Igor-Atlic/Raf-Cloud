package com.example.demo.controllers;

import com.example.demo.controllers.erors.ForbiddenException;
import com.example.demo.controllers.erors.UserDoesntExistException;
import com.example.demo.model.Permissions;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){this.userService = userService;}

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_read_users)){
            return userService.findAll();
        }else {
            throw new ForbiddenException("You don't have permission to see users");
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_read_users)){
            return userService.findById(id).orElseThrow(() -> new UserDoesntExistException("User with id=" + id + " doesn't exist"));
        }else {
            throw new ForbiddenException("You don't have permission to see users");
        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@Valid @RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_create_users)){
            return userService.createUser(user);
        }else {
            throw new ForbiddenException("You don't have permission to create a user");

        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        System.out.println(tem);
        if(tem.contains(Permissions.can_delete_users)){
            userService.deleteById(id);
        }else {
            System.out.println("Greska");
            throw new ForbiddenException("You don't have permission to delete a user");

        }
    }
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@Valid @RequestBody User user, @PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashSet tem = (HashSet) authentication.getCredentials();
        if(tem.contains(Permissions.can_update_users)){
            return userService.updateUser(user,id);
        }else {
            throw new ForbiddenException("You don't have permission to update a user");
        }
    }
}
