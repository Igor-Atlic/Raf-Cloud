package com.example.demo.services;

import com.example.demo.model.Error;
import com.example.demo.model.Machine;
import com.example.demo.repositories.ErrorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorService {

    private ErrorRepo errorRepo;
    @Autowired
    public ErrorService(ErrorRepo errorRepo){ this.errorRepo = errorRepo;}
    public List<Error> findAll(String user){return (List<Error>)errorRepo.findAllByCreatedByEmailLike(user);}
    public Error createError(Error error){ return this.errorRepo.save(error); }

}
