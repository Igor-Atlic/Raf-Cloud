package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorId;

    @Column
    private String message;
    @Column
    private Date date;

    @Column
    private Long machineId;

    @Column
    private String fun;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "createdBy", referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private User createdBy;

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getFun() {
        return fun;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }
}
