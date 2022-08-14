package com.example.demo.bootstrap;

import com.example.demo.model.Machine;
import com.example.demo.model.Permissions;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repositories.MachineRepo;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {


    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MachineRepo machineRepo;

    @Autowired
    public BootstrapData(UserRepo userRepository, PasswordEncoder passwordEncoder,MachineRepo machineRepo) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.machineRepo = machineRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");



        User user1 = new User();
        user1.setLastName("Atlic");
        user1.setFirstName("Igor");
        user1.setEmail("igor@gmail.com");
        user1.setPassword(this.passwordEncoder.encode("12341234"));
        HashSet<Permissions> tem = new HashSet<>();
        tem.add(Permissions.can_create_users);
        tem.add(Permissions.can_read_users);
        tem.add(Permissions.can_delete_users);
        tem.add(Permissions.can_update_users);

        tem.add(Permissions.can_search_machines);
        tem.add(Permissions.can_start_machines);
        tem.add(Permissions.can_stop_machines);
        tem.add(Permissions.can_restart_machines);
        tem.add(Permissions.can_create_machines);
        tem.add(Permissions.can_destroy_machines);

        user1.setPermissions(tem);
        this.userRepository.save(user1);

        User user2 = new User();
        user2.setLastName("User2");
        user2.setFirstName("User2 F");
        user2.setEmail("igor@gmail1.com");
        user2.setPassword(this.passwordEncoder.encode("12341234"));
        HashSet<Permissions> tem1 = new HashSet<>();
        tem1.add(Permissions.can_read_users);
        tem1.add(Permissions.can_search_machines);
        tem1.add(Permissions.can_start_machines);
        tem1.add(Permissions.can_stop_machines);
        tem1.add(Permissions.can_create_machines);
        tem1.add(Permissions.can_destroy_machines);
        user2.setPermissions(tem1);
        this.userRepository.save(user2);

        User user3 = new User();
        user3.setLastName("Atlic");
        user3.setFirstName("Igor");
        user3.setEmail("igor@gmail2.com");
        user3.setPassword(this.passwordEncoder.encode("12341234"));
        HashSet<Permissions> tem3 = new HashSet<>();
        user3.setPermissions(tem3);
        this.userRepository.save(user3);

        Machine m = new Machine();
        m.setMachineName("Primer 1");
        m.setActive(Boolean.TRUE);
        m.setStatus(Status.STOPPED);
        m.setCreatedAt(LocalDate.now());
        m.setCreatedBy(user1);
        this.machineRepo.save(m);


        Machine m1 = new Machine();
        m1.setMachineName("Primer 2");
        m1.setActive(Boolean.TRUE);
        m1.setStatus(Status.STOPPED);
        m1.setCreatedAt(LocalDate.of(2022,2,5));
        m1.setCreatedBy(user1);
        this.machineRepo.save(m1);

        Machine m2 = new Machine();
        m2.setMachineName("Primer 1");
        m2.setActive(Boolean.TRUE);
        m2.setStatus(Status.STOPPED);
        m2.setCreatedAt(LocalDate.now());
        m2.setCreatedBy(user2);
        this.machineRepo.save(m2);


        Machine m3 = new Machine();
        m3.setMachineName("Primer 2");
        m3.setActive(Boolean.TRUE);
        m3.setStatus(Status.STOPPED);
        m3.setCreatedAt(LocalDate.now());
        m3.setCreatedBy(user2);
        this.machineRepo.save(m3);

        System.out.println("Data loaded!");
    }
}
