package com.example.demo.repositories;

import com.example.demo.model.Error;
import com.example.demo.model.Machine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorRepo extends CrudRepository<Error, Long> {

    public List<Error> findAllByCreatedByEmailLike(String email);
}
