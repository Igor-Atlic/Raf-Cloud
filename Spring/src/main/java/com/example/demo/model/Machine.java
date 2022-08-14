package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineId;

    @Column
    @NotBlank(message = "Name is mandatory")
    private String machineName;

    @Column(name = "status",nullable = false)
    private Status status;

    @Column
    private LocalDate createdAt;

    @Column(nullable = false)
    private Boolean active;

    @Column
    private Boolean mLock = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "createdBy", referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private User createdBy;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Boolean getmLock() {
        return mLock;
    }

    public void setmLock(Boolean mLock) {
        this.mLock = mLock;
    }
}
