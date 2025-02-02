package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "compensationCollection")
public class Compensation {
    
    @Id
    private String compensationId;
    private BigDecimal salary;
    private LocalDate effectiveDate;
    private String employeeId;

    public Compensation() {}

    public Compensation(String compensationId, BigDecimal salary, LocalDate effectiveDate, String employeeId) {
        this.compensationId = compensationId;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
        this.employeeId = employeeId;
    }

    public String getCompensationId() {
        return this.compensationId;
    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
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
