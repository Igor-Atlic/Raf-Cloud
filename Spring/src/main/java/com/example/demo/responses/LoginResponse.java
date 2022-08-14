package com.example.demo.responses;

import com.example.demo.model.Permissions;
import lombok.Data;

import java.util.HashSet;

@Data
public class LoginResponse {
    private String jwt;
    private HashSet<Permissions> permissions = new HashSet<>();
    public LoginResponse(String jwt, Object obj) {
        this.jwt = jwt;
        this.permissions = (HashSet<Permissions>) obj;
    }
}
