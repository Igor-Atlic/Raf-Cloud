package com.example.demo.repositories;

import com.example.demo.model.Machine;
import com.example.demo.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepo extends CrudRepository<Machine, Long> {

    public List<Machine> findAllByCreatedByEmailLikeAndActiveIsTrue(String email) ;
    public List<Machine> findAllByCreatedByEmailLikeAndStatusInAndMachineNameContainsIgnoreCaseAndCreatedAtBetweenAndActiveIsTrue(String email, List<Status> status, String machineName, LocalDate dateFrom, LocalDate dateTo);

}
