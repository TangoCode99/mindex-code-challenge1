package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class Compensation {
    
    @Id
    private String id;

    private BigDecimal salary;
    private LocalDate effectiveDate;
    private String employeeId;

    public Compensation() {}

    public Compensation(BigDecimal salary, LocalDate effectiveDate, String employeeId) {
        this.salary = salary;
        this.effectiveDate = effectiveDate;
        this.employeeId = employeeId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
}
