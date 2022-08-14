package com.example.demo.services;

import com.example.demo.controllers.erors.UserDoesntExistException;
import com.example.demo.model.Machine;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    private MachineService machineService;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, MachineService machineService){ this.userRepo = userRepo; this.passwordEncoder = passwordEncoder;
        this.machineService = machineService;
    }

    public List<User> findAll(){return (List<User>)userRepo.findAll();}
    public User findUserByUsername(String username){return userRepo.findByEmail(username);}
    public Optional<User> findById(Long userID) {
        return userRepo.findById(userID);
    }
    public void deleteById(Long userID) { userRepo.deleteById(userID); }
    public User createUser(User user){
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    public User updateUser(User user, Long userID){
        Optional<User> tem = userRepo.findById(userID);
        User tem1 = tem.orElseThrow(() -> new UserDoesntExistException("User with id=" + userID + " doesn't exist"));
        if(!tem1.getEmail().equals(user.getEmail())){
            List<Machine> m = this.machineService.findAllUser(tem1.getEmail());
            for (Machine machine:m) {
                machine.setCreatedBy(user);
                this.machineService.updateMachine(machine, machine.getMachineId());
            }
            tem1.setEmail(user.getEmail());
        }

        tem1.setFirstName(user.getFirstName());
        tem1.setLastName(user.getLastName());
        tem1.setPermissions(user.getPermissions());
        return userRepo.save(tem1);

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = this.userRepo.findByEmail(username);

        if(myUser == null) {
            throw new UsernameNotFoundException("User email "+username+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), new ArrayList<>());

    }
}
