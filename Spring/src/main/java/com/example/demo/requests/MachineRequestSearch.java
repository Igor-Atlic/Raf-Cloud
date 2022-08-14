package com.example.demo.requests;

import com.example.demo.model.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class MachineRequestSearch {

    private String name;
    private List<Status> status;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}



