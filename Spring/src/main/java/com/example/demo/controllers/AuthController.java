package com.example.demo.controllers;

import com.example.demo.requests.LoginRequest;
import com.example.demo.responses.LoginResponse;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception   e){
            e.printStackTrace();
            Map<String, String> errors = new HashMap<>();
            errors.put("reason",e.getMessage());
            return new ResponseEntity<>( errors,
                    HttpStatus.BAD_REQUEST);

        }

        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getUsername()) , userService.findUserByUsername(loginRequest.getUsername()).getPermissions()));
    }

}
