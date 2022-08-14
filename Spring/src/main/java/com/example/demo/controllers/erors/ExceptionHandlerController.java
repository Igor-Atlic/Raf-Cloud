package com.example.demo.controllers.erors;

import com.example.demo.model.Error;
import com.example.demo.services.ErrorService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {


    private ErrorService errorService;
    private UserService userService;

    @Autowired
    public ExceptionHandlerController(ErrorService errorService, UserService userService){
        this.errorService = errorService;
        this.userService = userService;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error: exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("reason",  errorMessage);
        }

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> handleSQLExceptions(IllegalStateException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason", exception.getMessage());

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleSQL1Exceptions(SQLIntegrityConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason", exception.getMessage());

        return errors;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public Map<String,String> handleForbiddenExceptions(ForbiddenException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason",exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserDoesntExistException.class)
    public Map<String,String> handleUserDoesntExistException(UserDoesntExistException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason",exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MachineDoesntExistException.class)
    public Map<String,String> handleMachineDoesntExistException(MachineDoesntExistException exception) {
        System.out.println("EGee");
        Map<String, String> errors = new HashMap<>();
        errors.put("reason",exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MachineIsLockedException.class)
    public Map<String,String> handleMachineIsLockedException(MachineIsLockedException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason",exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MachineIsNotActive.class)
    public Map<String, String> handleMachineIsNotActive(MachineIsNotActive exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason", exception.getMessage());

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MachineBadRequest.class)
    public Map<String, String> handleMachineBadRequest(MachineBadRequest exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("reason", exception.getMessage());

        return errors;
    }

    @ExceptionHandler(ScheduleError.class)
    public void handleScheduleError(ScheduleError exception) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Error tem = new Error();
        tem.setCreatedBy(userService.findUserByUsername(authentication.getName()));
        tem.setMessage(exception.getMessage());
        System.out.println("Greskaaaa");
        errorService.createError(tem);


    }
}
